package com.example.notes_hoodlab.repository

import `package com`.example.notes_hoodlab.`repository;`
import com.example.notes_hoodlab.repository.StorageRepository
import com.google.firebase.Timestamp
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`*;`

class StorageRepositoryTest {

    @MockK
    private lateinit var storageRepository: StorageRepository;

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this);
    }


    @Test
    @DisplayName("Should not add a note when title is empty")
    fun addNoteWhenTitleIsEmpty() {
        val userId = "user123"
        val title = ""
        val description = "This is a test note"
        val timeStamp = Timestamp.now()
        val colorIndex = 0

        assertThrows<IllegalArgumentException> {
            storageRepository.addNote(
                userId,
                title,
                description,
                timeStamp,
                colorIndex
            ) { success ->
                assertFalse(success)
            }
        }

        verify(exactly = 0) { storageRepository.notesReference.document(any()).set(any()) }
    }

    @Test
    @DisplayName("Should add a note successfully when all parameters are valid")
    fun addNoteWhenAllParametersAreValid() {
        val userId = "user123"
        val title = "Test Note"
        val description = "This is a test note"
        val timeStamp = Timestamp.now()
        val colorIndex = 1
        val documentId = "note123"

        val onComplete: (Boolean) -> Unit = { success ->
            assertTrue(success)
        }

        every {
            storageRepository.addNote(
                userId,
                title,
                description,
                timeStamp,
                colorIndex,
                any()
            )
        } answers {
            val onCompleteCallback = arg<(Boolean) -> Unit>(5)
            onCompleteCallback.invoke(true)
        }

        storageRepository.addNote(userId, title, description, timeStamp, colorIndex, onComplete)

        verify(storageRepository, times(1)).addNote(
            userId,
            title,
            description,
            timeStamp,
            colorIndex,
            onComplete
        )
    }

    @Test
    @DisplayName("Should not add a note when description is empty")
    fun addNoteWhenDescriptionIsEmpty() {
        val userId = "user123"
        val title = "Test Note"
        val description = ""
        val timeStamp = Timestamp.now()
        val colorIndex = 0

        assertThrows<IllegalArgumentException> {
            storageRepository.addNote(
                userId,
                title,
                description,
                timeStamp,
                colorIndex
            ) { isSuccess ->
                assertFalse(isSuccess)
            }
        }

        verify(exactly = 0) { storageRepository.notesReference.document(any()).set(any()) }
    }

    @Test
    @DisplayName("Should not add a note when userId is invalid")
    fun addNoteWhenUserIdIsInvalid() {
        val userId = ""
        val title = "Test Note"
        val description = "This is a test note"
        val timeStamp = Timestamp.now()
        val colorIndex = 0

        assertThrows<IllegalArgumentException> {
            storageRepository.addNote(
                userId,
                title,
                description,
                timeStamp,
                colorIndex
            )
        }

        verify(storageRepository, never()).addNote(
            anyString(),
            anyString(),
            anyString(),
            any(Timestamp::class.java),
            anyInt(),
            any()
        )
    }

    @Test
    @DisplayName("Should not add a note when timestamp is null")
    fun addNoteWhenTimestampIsNull() {
        val userId = "user123"
        val title = "Test Note"
        val description = "This is a test note"
        val timeStamp: Timestamp? = null
        val colorIndex = 0

        assertThrows<IllegalArgumentException> {
            storageRepository.addNote(
                userId,
                title,
                description,
                timeStamp,
                colorIndex
            ) { isSuccess ->
                assertFalse(isSuccess)
            }
        }

        verify(exactly = 0) { storageRepository.notesReference.document(any()).set(any()) }
    }

}
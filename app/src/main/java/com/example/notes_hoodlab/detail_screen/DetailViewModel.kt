package com.example.notes_hoodlab.detail_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.notes_hoodlab.model.Notes
import com.example.notes_hoodlab.repository.StorageRepository
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser

class DetailViewModel(private val repository: StorageRepository = StorageRepository()) :
    ViewModel() {

    var detailUiState by mutableStateOf(DetailUiState())
        private set

    private val hasUser: Boolean
        get() = repository.hasUser()

    private val user:FirebaseUser?
        get() = repository.user()

    fun onColorChange(colorIndex: Int) {
        detailUiState = detailUiState.copy(colorIndex = colorIndex)
    }

    fun onTitleChange(title: String) {
        detailUiState = detailUiState.copy(title = title)
    }

    fun onDescriptionChange(description: String) {
        detailUiState = detailUiState.copy(description = description)
    }

    fun addNote() {
        if (hasUser) {
            repository.addNote(
                userId = user?.uid.toString(),
                title = detailUiState.title,
                description = detailUiState.description,
                timestamp = Timestamp.now(),
                color = detailUiState.colorIndex,
            ) {
                detailUiState = detailUiState.copy(noteAdded = it)
            }
        }
    }

    fun setEditFields(note: Notes) {
        detailUiState = detailUiState.copy(
            title = note.title,
            description = note.description,
            colorIndex = note.colorIndex,
        )
    }

    fun getNote(noteId: String, onError: (Throwable) -> Unit, onSuccess: (Notes?) -> Unit) {
        repository.getNote(noteId, onError = {}) {
            detailUiState = detailUiState.copy(
                selectedNote = it
            )
            detailUiState.selectedNote?.let {
                setEditFields(it)
            }
        }
    }

    fun updateNote(noteId: String) {
        repository.updateNote(
            noteId = noteId,
            title = detailUiState.title,
            note = detailUiState.description,
            color = detailUiState.colorIndex,
        ) {
            detailUiState = detailUiState.copy(
                noteUpdated = it
            )
        }
    }

    fun resetNoteAdded() {
        detailUiState = detailUiState.copy(
            noteAdded = false,
            noteUpdated = false
        )
    }

    fun resetState() {
        detailUiState = DetailUiState()
    }

}

data class DetailUiState(
    val title: String = "",
    val description: String = "",
    val colorIndex: Int = 0,
    val noteAdded: Boolean = false,
    val noteUpdated: Boolean = false,
    val selectedNote: Notes? = null
)
package com.example.notes_hoodlab.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes_hoodlab.model.Notes
import com.example.notes_hoodlab.repository.Resources
import com.example.notes_hoodlab.repository.StorageRepository
import kotlinx.coroutines.launch


class HomeViewModel(private val repository: StorageRepository = StorageRepository()) : ViewModel() {

    var homeUiState by mutableStateOf(HomeUiState())
        private set

    val user = repository.user()
    val hasUser = repository.hasUser()
    private val userId = repository.getUserId()

    fun loadNotes() {
        if (hasUser) {
            getUserNotes(userId)
        } else {
            homeUiState = homeUiState.copy(
                notesList = Resources.Error(
                    throwable = Throwable(message = "No user")
                )
            )
        }
    }

    private fun getUserNotes(userId: String) = viewModelScope.launch {
        repository.getUserNotes(userId).collect {
            homeUiState = homeUiState.copy(notesList = it)
        }
    }

    fun deleteNote(noteId: String) = repository.deleteNote(noteId) {
        homeUiState = homeUiState.copy(
            noteDeletedStatus = it
        )
    }

    fun signOut() = repository.signOut()

}

data class HomeUiState(
    val notesList: Resources<List<Notes>> = Resources.Loading(),
    val noteDeletedStatus: Boolean = false,
)
package com.example.notes_hoodlab.login

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes_hoodlab.repository.AuthRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginViewModel(
    private var repository: AuthRepository = AuthRepository()
): ViewModel() {

    constructor() : this(AuthRepository()) {
        repository = AuthRepository()
    }
    val currentUser = repository.currentUser
    val hasUser: Boolean
        get() = repository.hasUser()

    var loginUiState by mutableStateOf(LoginUiState())
        private set

    fun onUserNameChanged(userName: String){
        loginUiState = loginUiState.copy(userName = userName)
    }

    fun onPasswordNameChange(password: String) {
        loginUiState = loginUiState.copy(password = password)
    }

    fun onUserNameChangeSignup(userName: String) {
        loginUiState = loginUiState.copy(userNameSignUp = userName)
    }

    fun onPasswordChangeSignup(password: String) {
        loginUiState = loginUiState.copy(passwordSignUp = password)
    }

    fun onConfirmPasswordChange(password: String) {
        loginUiState = loginUiState.copy(confirmPasswordSignUp = password)
    }

    private fun validateLoginForm() =
        loginUiState.userName.isNotBlank() && loginUiState.password.isNotBlank()

    private fun validateSignupForm() =
        loginUiState.userNameSignUp.isNotBlank() && loginUiState.passwordSignUp.isNotBlank() && loginUiState.confirmPasswordSignUp.isNotBlank()

    fun makeUser(context: Context) = viewModelScope.launch {
        try {
            if(!validateSignupForm()){
                throw IllegalArgumentException("email and password must not be empty")
            }
            loginUiState = loginUiState.copy(isLoading = true)
            if (loginUiState.passwordSignUp != loginUiState.confirmPasswordSignUp){
                throw IllegalArgumentException("password do not match")
            }
            loginUiState = loginUiState.copy(isSignUpError = null)
            repository.makeUser(
                loginUiState.userNameSignUp,
                loginUiState.passwordSignUp
            ){isSuccessful ->
                loginUiState = if (isSuccessful){
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    loginUiState.copy(isSuccessLogin = true)
                }else{
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                    loginUiState.copy(isSuccessLogin = false)
                }
            }
        }catch(e: Exception) {
            loginUiState = loginUiState.copy(isSignUpError = e.localizedMessage)
            e.printStackTrace()
        }finally {
            loginUiState = loginUiState.copy(isLoading = false)
        }
    }

    fun loginUser(context: Context) = viewModelScope.launch {
        try {
            if (!validateLoginForm()) {
                throw IllegalArgumentException("email and password can not be empty")
            }
            loginUiState = loginUiState.copy(isLoading = true)
            loginUiState = loginUiState.copy(isLoginError = null)
            repository.loginUser(
                loginUiState.userName,
                loginUiState.password
            ) { isSuccessful ->
                if (isSuccessful) {
                    Toast.makeText(
                        context,
                        "success Login",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginUiState = loginUiState.copy(isSuccessLogin = true)
                } else {
                    Toast.makeText(
                        context,
                        "Failed Login",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginUiState = loginUiState.copy(isSuccessLogin = false)
                }

            }
        }catch(e: Exception) {
            loginUiState = loginUiState.copy(isLoginError = e.localizedMessage)
            e.printStackTrace()
        }finally {
            loginUiState = loginUiState.copy(isLoading = false)
        }
    }

}

data class LoginUiState(
    val userName: String = "",
    val password: String = "",
    val userNameSignUp: String = "",
    val passwordSignUp: String = "",
    val confirmPasswordSignUp: String = "",
    val isLoading: Boolean = false,
    val isSuccessLogin: Boolean = false,
    val isSignUpError: String? = null,
    val isLoginError: String? = null
)
package com.ivan.sistema_reservacion.ui.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivan.sistema_reservacion.data.local.entities.UserEntity
import com.ivan.sistema_reservacion.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    val email = mutableStateOf("")
    val password = mutableStateOf("")

    val loggedUser = mutableStateOf<UserEntity?>(null)
    val errorMessage = mutableStateOf<String?>(null)

    fun login() {
        viewModelScope.launch {
            val user = userRepository.login(email.value, password.value)
            if (user != null) {
                loggedUser.value = user
                errorMessage.value = null
            } else {
                errorMessage.value = "Credenciales incorrectas"
            }
        }
    }
}

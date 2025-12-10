package com.ivan.sistema_reservacion.ui.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivan.sistema_reservacion.data.local.entities.UserEntity
import com.ivan.sistema_reservacion.data.repository.UserRepository
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    val name = mutableStateOf("")
    val lastName = mutableStateOf("")
    val email = mutableStateOf("")
    val phone = mutableStateOf("")
    val password = mutableStateOf("")

    val registerSuccess = mutableStateOf(false)

    fun register() {
        viewModelScope.launch {
            val user = UserEntity(
                name = name.value,
                lastName = lastName.value,
                email = email.value,
                phone = phone.value,
                password = password.value
            )
            userRepository.registerUser(user)
            registerSuccess.value = true
        }
    }
}

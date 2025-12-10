package com.ivan.sistema_reservacion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ivan.sistema_reservacion.data.repository.ReservationRepository
import com.ivan.sistema_reservacion.data.repository.UserRepository
import com.ivan.sistema_reservacion.ui.booking.BookingViewModel
import com.ivan.sistema_reservacion.ui.login.LoginViewModel
import com.ivan.sistema_reservacion.ui.register.RegisterViewModel

class AppViewModelFactory(
    private val userRepository: UserRepository,
    private val reservationRepository: ReservationRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) ->
                LoginViewModel(userRepository) as T
            modelClass.isAssignableFrom(RegisterViewModel::class.java) ->
                RegisterViewModel(userRepository) as T
            modelClass.isAssignableFrom(BookingViewModel::class.java) ->
                BookingViewModel(reservationRepository) as T
            else -> throw IllegalArgumentException("ViewModel no soportado: ${modelClass.name}")
        }
    }
}
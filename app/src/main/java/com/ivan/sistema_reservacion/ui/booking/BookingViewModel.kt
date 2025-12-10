package com.ivan.sistema_reservacion.ui.booking

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivan.sistema_reservacion.data.local.entities.ReservationEntity
import com.ivan.sistema_reservacion.data.repository.ReservationRepository
import kotlinx.coroutines.launch

class BookingViewModel(
    private val reservationRepository: ReservationRepository
) : ViewModel() {

    val clientName = mutableStateOf("")
    val email = mutableStateOf("")
    val date = mutableStateOf("")
    val people = mutableStateOf("")
    val area = mutableStateOf("")
    val tableNumber = mutableStateOf("")
    val extras = mutableStateOf("")

    val saveSuccess = mutableStateOf(false)

    fun createReservation(userId: Int) {
        viewModelScope.launch {
            val reservation = ReservationEntity(
                userId = userId,
                clientName = clientName.value,
                email = email.value,
                date = date.value,
                people = people.value.toIntOrNull() ?: 1,
                area = area.value,
                tableNumber = tableNumber.value,
                extras = extras.value
            )
            reservationRepository.createReservation(reservation)
            saveSuccess.value = true
        }
    }
}
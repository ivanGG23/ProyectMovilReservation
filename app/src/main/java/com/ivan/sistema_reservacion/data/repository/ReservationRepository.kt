package com.ivan.sistema_reservacion.data.repository

import com.ivan.sistema_reservacion.data.local.dao.ReservationDao
import com.ivan.sistema_reservacion.data.local.entities.ReservationEntity
import kotlinx.coroutines.flow.Flow

class ReservationRepository(private val reservationDao: ReservationDao) {

    suspend fun createReservation(reservation: ReservationEntity) {
        reservationDao.insertReservation(reservation)
    }

    fun getReservationsForUser(userId: Int): Flow<List<ReservationEntity>> {
        return reservationDao.getReservationsForUser(userId)
    }

    suspend fun updateReservation(reservation: ReservationEntity) {
        reservationDao.updateReservation(reservation)
    }
}
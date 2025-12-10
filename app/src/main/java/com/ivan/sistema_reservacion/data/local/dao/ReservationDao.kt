package com.ivan.sistema_reservacion.data.local.dao

import androidx.room.*
import com.ivan.sistema_reservacion.data.local.entities.ReservationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservationDao {

    @Insert
    suspend fun insertReservation(reservation: ReservationEntity)

    @Update
    suspend fun updateReservation(reservation: ReservationEntity)

    @Query("SELECT * FROM reservations WHERE userId = :userId ORDER BY date DESC")
    fun getReservationsForUser(userId: Int): Flow<List<ReservationEntity>>
}

package com.ivan.sistema_reservacion.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reservations")
data class ReservationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val clientName: String,
    val email: String,
    val date: String,
    val people: Int,
    val area: String,
    val tableNumber: String,
    val extras: String,
    val status: String = "Pendiente"
)

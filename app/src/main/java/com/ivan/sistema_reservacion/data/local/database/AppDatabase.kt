package com.ivan.sistema_reservacion.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ivan.sistema_reservacion.data.local.dao.ReservationDao
import com.ivan.sistema_reservacion.data.local.dao.UserDao
import com.ivan.sistema_reservacion.data.local.entities.ReservationEntity
import com.ivan.sistema_reservacion.data.local.entities.UserEntity

@Database(
    entities = [UserEntity::class, ReservationEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun reservationDao(): ReservationDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "sistema_reservacion_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
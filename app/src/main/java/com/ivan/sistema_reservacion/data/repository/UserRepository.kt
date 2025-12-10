package com.ivan.sistema_reservacion.data.repository

import com.ivan.sistema_reservacion.data.local.dao.UserDao
import com.ivan.sistema_reservacion.data.local.entities.UserEntity

class UserRepository(private val userDao: UserDao) {

    suspend fun registerUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    suspend fun login(email: String, password: String): UserEntity? {
        return userDao.login(email, password)
    }

    suspend fun getUser(id: Int): UserEntity? = userDao.getUserById(id)

    suspend fun updateUser(user: UserEntity) = userDao.updateUser(user)
}

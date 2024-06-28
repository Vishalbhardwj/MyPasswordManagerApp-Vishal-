package com.example.mypasswordmanagerapp

import androidx.lifecycle.LiveData
import com.example.mypasswordmanagerapp.Db.PasswordDao
import com.example.mypasswordmanagerapp.Db.PasswordEntity

class PasswordRepository(private val passwordDao: PasswordDao) {

    val allPasswords: LiveData<List<PasswordEntity>> = passwordDao.getAllPasswords()

    suspend fun insert(password: PasswordEntity) {
        passwordDao.insert(password)
    }

    suspend fun update(password: PasswordEntity) {
        passwordDao.update(password)
    }

    suspend fun delete(password: PasswordEntity) {
        passwordDao.delete(password)
    }
}
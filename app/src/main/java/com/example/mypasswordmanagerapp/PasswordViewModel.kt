package com.example.mypasswordmanagerapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mypasswordmanagerapp.Db.PasswordDatabase
import com.example.mypasswordmanagerapp.Db.PasswordEntity
import kotlinx.coroutines.launch
import javax.crypto.spec.SecretKeySpec

class PasswordViewModel(application: Application):AndroidViewModel(application) {
    private val repository: PasswordRepository
    val passwords: LiveData<List<PasswordEntity>>

    init {
        val passwordDao = PasswordDatabase.getDatabase(application).passwordDao()
        repository = PasswordRepository(passwordDao)
        passwords = repository.allPasswords
    }

    fun addPassword(accountType: String, usernameEmail: String, password: String) {
//        val encryptedPassword = EncryptionUtil.encrypt(password, secretKey)
        val newPassword = PasswordEntity(accountType = accountType, usernameEmail = usernameEmail, password = password)
        viewModelScope.launch {
            repository.insert(newPassword)
        }
    }

    fun updatePassword(password: PasswordEntity) {
        viewModelScope.launch {
            repository.update(password)
        }
    }

    fun deletePassword(password: PasswordEntity) {
        viewModelScope.launch {
            repository.delete(password)
        }
    }
}
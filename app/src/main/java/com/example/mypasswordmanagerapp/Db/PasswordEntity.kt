package com.example.mypasswordmanagerapp.Db

import androidx.room.*

@Entity(tableName = "password_table")
data class PasswordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val accountType: String,
    val usernameEmail: String,
    val password: String
)

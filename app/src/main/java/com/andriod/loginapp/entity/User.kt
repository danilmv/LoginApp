package com.andriod.loginapp.entity

import java.util.*

data class User(
    val id: String = UUID.randomUUID().toString(),
    val login: String,
    val email: String,
    val password: String,
)

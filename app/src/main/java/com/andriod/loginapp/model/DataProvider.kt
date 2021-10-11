package com.andriod.loginapp.model

import com.andriod.loginapp.entity.User

abstract class DataProvider {
    val users = mutableMapOf<String, User>()

    abstract fun getUser(login: String): User?
    abstract fun saveUser(user: User)
    abstract fun checkLoginExists(login: String): Boolean
    abstract fun checkPassword(login: String, password: String): Boolean
}
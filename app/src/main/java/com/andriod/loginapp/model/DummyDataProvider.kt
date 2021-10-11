package com.andriod.loginapp.model

import com.andriod.loginapp.entity.User

class DummyDataProvider : DataProvider() {
    override fun getUser(login: String) = users[login]
    override fun saveUser(user: User) {
        users[user.login] = user
    }
    override fun checkLoginExists(login: String) = users[login] != null
    override fun checkPassword(login: String, password: String) = users[login]?.password == password
}
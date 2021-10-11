package com.andriod.loginapp.signin

import com.andriod.loginapp.base.BaseContract
import com.andriod.loginapp.entity.User

class SigninContract : BaseContract() {
    enum class Error {
        WRONG_LOGIN, WRONG_EMAIL, WRONG_PASSWORD, PASSWORDS_NOT_MATCH, LOGIN_EXISTS
    }

    interface View : BaseContract.View {
        fun setError(error: Error)
        fun showUser(user: User)
        fun showLogin()
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun onSave(login: String, email: String, password: String, password2: String)
        fun onCancel()
    }
}
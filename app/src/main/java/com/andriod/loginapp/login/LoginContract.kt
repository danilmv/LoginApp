package com.andriod.loginapp.login

import com.andriod.loginapp.entity.User
import com.andriod.loginapp.base.BaseContract

class LoginContract: BaseContract() {
    enum class Error{
        WRONG_PASSWORD, NO_LOGIN
    }

    interface View: BaseContract.View{
        fun setError(error: Error)
        fun showSignIn()
        fun showForget(user: User)
        fun showUser(user: User)
    }

    interface Presenter: BaseContract.Presenter{
        fun onLogin(login: String, pass:String)
        fun onSignIn()
        fun onForgetPassword(login: String)
    }
}
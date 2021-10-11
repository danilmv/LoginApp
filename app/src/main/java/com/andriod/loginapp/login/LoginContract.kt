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
        fun showForget(pass: String)
        fun showUser(user: User)
    }

    interface Presenter: BaseContract.Presenter<View>{
        fun onLogin(login: String, pass:String)
        fun onSignIn()
        fun onForgetPassword(login: String)
    }
}
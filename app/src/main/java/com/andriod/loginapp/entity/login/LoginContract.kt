package com.andriod.loginapp.entity.login

import com.andriod.loginapp.entity.User

class LoginContract {
    enum class ViewState{
        IDLE, LOADING, COMPLETE
    }
    enum class Error{
        WRONG_PASSWORD, NO_LOGIN
    }

    interface View{
        fun setState(state: ViewState)
        fun setError(error: Error)
        fun showSignIn()
        fun showForget(user: User)
        fun showUser(user: User)
    }

    interface Presenter{
        fun onAttach(view: View)
        fun onDetach()

        fun onLogin(login: String, pass:String)
        fun onSignIn()
        fun onForgetPassword(login: String)
    }
}
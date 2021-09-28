package com.andriod.loginapp.entity.login

import com.andriod.loginapp.entity.User

class LoginContract {
    enum class ViewState{
        IDLE, LOADING, COMPLETE
    }

    interface View{
        fun setState(state: ViewState)
        fun setUser(user:User)
        fun setError()
    }

    interface Presenter{
        fun onAttach(view: View)
        fun onDetach()

        fun onLogin(login: String, pass:String)
        fun onSignIn()
        fun onForgetPassword()
    }
}
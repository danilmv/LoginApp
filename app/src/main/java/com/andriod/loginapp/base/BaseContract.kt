package com.andriod.loginapp.base

import com.andriod.loginapp.login.LoginContract

abstract class BaseContract {
    enum class ViewState {
        IDLE, LOADING, COMPLETE
    }

    interface View {
        fun setState(state: ViewState)
    }

    interface Presenter {
        fun onAttach(view: LoginContract.View)
        fun onDetach()
    }
}
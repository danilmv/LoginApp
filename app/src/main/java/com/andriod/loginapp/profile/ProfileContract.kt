package com.andriod.loginapp.profile

import com.andriod.loginapp.base.BaseContract

class ProfileContract: BaseContract() {
    interface View: BaseContract.View{
        fun showLogin()
    }

    interface Presenter: BaseContract.Presenter{
        fun onLogout()
    }
}
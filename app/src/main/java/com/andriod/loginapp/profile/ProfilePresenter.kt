package com.andriod.loginapp.profile

import com.andriod.loginapp.base.BaseContract

class ProfilePresenter : ProfileContract.Presenter {

    private var view: ProfileContract.View? = null

    override fun onLogout() {
        view?.showLogin()
    }

    override fun onAttach(view: ProfileContract.View) {
        this.view = view

        view.setState(BaseContract.ViewState.LOADING)
        BaseContract.delayedRun { view.setState(BaseContract.ViewState.IDLE) }
    }

    override fun onDetach() {
        view = null
    }
}
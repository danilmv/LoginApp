package com.andriod.loginapp.profile

import com.andriod.loginapp.base.BaseContract

class ProfilePresenter : ProfileContract.Presenter {

    private var view: ProfileContract.View? = null

    override fun onLogout() {
        view?.showLogin()
    }

    override fun onAttach(view: BaseContract.View) {
        this.view = view as ProfileContract.View

        view.setState(BaseContract.ViewState.LOADING)
        BaseContract.delayedRun { view.setState(BaseContract.ViewState.IDLE) }.invoke()
    }

    override fun onDetach() {
        view = null
    }
}
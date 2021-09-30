package com.andriod.loginapp.login

import com.andriod.loginapp.base.BaseContract
import com.andriod.loginapp.base.BaseContract.Companion.delayedRun
import com.andriod.loginapp.entity.User


class LoginPresenter : LoginContract.Presenter {
    private var view: LoginContract.View? = null
    private var users = mutableMapOf<String, User>()

    override fun onAttach(view: BaseContract.View) {
        this.view = view as LoginContract.View
        view.setState(BaseContract.ViewState.LOADING)

        delayedRun {
            this.view?.setState(BaseContract.ViewState.IDLE)
        }
    }

    override fun onDetach() {
        view = null
    }

    override fun onLogin(login: String, pass: String) {
        view?.apply {
            setState(BaseContract.ViewState.LOADING)
            delayedRun {
                setState(BaseContract.ViewState.COMPLETE)
                val user = users[login]
                if (user != null && user.password == pass) {
                    showUser(user)
                } else {
                    setError(LoginContract.Error.WRONG_PASSWORD)
                }
            }
        }
    }

    override fun onSignIn() {
        view?.showSignIn()
    }

    override fun onForgetPassword(login: String) {
        view?.apply {
            setState(BaseContract.ViewState.LOADING)
            delayedRun {
                setState(BaseContract.ViewState.COMPLETE)
                val user = users[login]
                if (user != null) {
                    showForget(user)
                } else {
                    setError(LoginContract.Error.NO_LOGIN)
                }
            }
        }
    }
}
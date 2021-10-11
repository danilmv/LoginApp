package com.andriod.loginapp.login

import com.andriod.loginapp.base.BaseContract
import com.andriod.loginapp.base.BaseContract.Companion.delayedRun
import com.andriod.loginapp.model.DataProvider


class LoginPresenter(private val dataProvider: DataProvider) : LoginContract.Presenter {
    private var view: LoginContract.View? = null

    override fun onAttach(view: LoginContract.View) {
        this.view = view
        view.setState(BaseContract.ViewState.LOADING)

        delayedRun { this.view?.setState(BaseContract.ViewState.IDLE) }
    }

    override fun onDetach() {
        view = null
    }

    override fun onLogin(login: String, pass: String) {
        view?.apply {
            setState(BaseContract.ViewState.LOADING)
            delayedRun {
                setState(BaseContract.ViewState.COMPLETE)
                if (dataProvider.checkLoginExists(login) && dataProvider.checkPassword(
                        login,
                        pass
                    )
                ) {
                    showUser(dataProvider.getUser(login)!!)
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
                if (dataProvider.checkLoginExists(login)) {
                    showForget(dataProvider.getUser(login)!!.password)
                } else {
                    setError(LoginContract.Error.NO_LOGIN)
                }
            }
        }
    }
}
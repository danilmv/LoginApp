package com.andriod.loginapp.entity.login

import com.andriod.loginapp.entity.User
import java.lang.Thread.sleep

private const val SLEEP_TIME = 1000L

class LoginPresenter : LoginContract.Presenter {
    private var view: LoginContract.View? = null
    private var users = mutableMapOf<String, User>()

    private val delayedRun = { runnable: Runnable ->
        {
            Thread {
                sleep(SLEEP_TIME)
                runnable.run()
            }.start()
        }
    }

    override fun onAttach(view: LoginContract.View) {
        this.view = view

        view.setState(LoginContract.ViewState.LOADING)
        delayedRun { view.setState(LoginContract.ViewState.IDLE) }.invoke()
    }

    override fun onDetach() {
        view = null
    }

    override fun onLogin(login: String, pass: String) {
        view?.apply {
            setState(LoginContract.ViewState.LOADING)
            delayedRun {
                setState(LoginContract.ViewState.COMPLETE)
                val user = users[login]
                if (user != null && user.password == pass) {
                    showUser(user)
                } else {
                    setError(LoginContract.Error.WRONG_PASSWORD)
                }
            }.invoke()
        }
    }

    override fun onSignIn() {
        view?.showSignIn()
    }

    override fun onForgetPassword(login: String) {
        view?.apply {
            setState(LoginContract.ViewState.LOADING)
            delayedRun {
                setState(LoginContract.ViewState.COMPLETE)
                val user = users[login]
                if (user != null) {
                    showForget(user)
                } else {
                    setError(LoginContract.Error.NO_LOGIN)
                }
            }.invoke()
        }
    }
}
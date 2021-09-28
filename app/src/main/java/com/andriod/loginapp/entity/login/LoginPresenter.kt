package com.andriod.loginapp.entity.login

import com.andriod.loginapp.entity.User
import java.lang.Thread.sleep

private const val SLEEP_TIME = 3000L

class LoginPresenter : LoginContract.Presenter {
    private var view: LoginContract.View? = null
    private var users = mutableMapOf<String, User>()

    override fun onAttach(view: LoginContract.View) {
        this.view = view

        view.setState(LoginContract.ViewState.LOADING)
        Thread {
            sleep(SLEEP_TIME)
            view.setState(LoginContract.ViewState.IDLE)
        }.start()
    }

    override fun onDetach() {
        view = null
    }

    override fun onLogin(login: String, pass: String) {
        val user = users[login]
        if (user != null && user.password == pass) {
            view?.showUser(user)
        } else {
            view?.setError(LoginContract.Error.WRONG_PASSWORD)
        }
    }

    override fun onSignIn() {
        view?.showSignIn()
    }

    override fun onForgetPassword(login: String) {
        val user = users[login]
        if (user!= null){
            view?.showForget(user)
        }else{
            view?.setError(LoginContract.Error.NO_LOGIN)
        }
    }
}
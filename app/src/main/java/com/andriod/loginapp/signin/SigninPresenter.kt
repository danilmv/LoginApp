package com.andriod.loginapp.signin

import com.andriod.loginapp.base.BaseContract
import com.andriod.loginapp.base.BaseContract.Companion.delayedRun
import com.andriod.loginapp.entity.User
import com.andriod.loginapp.model.DataProvider

class SigninPresenter(private val dataProvider: DataProvider) : SigninContract.Presenter {
    private var view: SigninContract.View? = null

    override fun onSave(login: String, email: String, password: String, password2: String) {
        view?.apply {
            setState(BaseContract.ViewState.LOADING)
            delayedRun {
                setState(BaseContract.ViewState.IDLE)
                when {
                    login.isBlank() -> setError(SigninContract.Error.WRONG_LOGIN)
                    dataProvider.checkLoginExists(login) -> setError(SigninContract.Error.LOGIN_EXISTS)
                    email.isBlank() -> setError(SigninContract.Error.WRONG_EMAIL)
                    password.isBlank() -> setError(SigninContract.Error.WRONG_PASSWORD)
                    password != password2 -> setError(SigninContract.Error.PASSWORDS_NOT_MATCH)
                    else -> {
                        val user = User(login = login, email = email, password = password)
                        saveUser(user)
                        showUser(user)
                    }
                }
            }
        }
    }

    private fun saveUser(user: User) {
        dataProvider.saveUser(user)
    }

    override fun onCancel() {
        view?.showLogin()
    }

    override fun onAttach(view: SigninContract.View) {
        this.view = view

        view.setState(BaseContract.ViewState.LOADING)
        delayedRun { view.setState(BaseContract.ViewState.IDLE) }
    }

    override fun onDetach() {
        view = null
    }
}
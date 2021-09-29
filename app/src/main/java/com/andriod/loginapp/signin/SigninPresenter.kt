package com.andriod.loginapp.signin

import com.andriod.loginapp.base.BaseContract
import com.andriod.loginapp.base.BaseContract.Companion.delayedRun
import com.andriod.loginapp.entity.User

class SigninPresenter : SigninContract.Presenter {
    private var view: SigninContract.View? = null
    private var users = mutableMapOf<String, User>()

    override fun onSave(login: String, email: String, password: String, password2: String) {
        view?.apply {
            setState(BaseContract.ViewState.LOADING)
            delayedRun {
                setState(BaseContract.ViewState.IDLE)
                when {
                    login.isBlank() -> setError(SigninContract.Error.WRONG_LOGIN)
                    users[login] != null -> setError(SigninContract.Error.LOGIN_EXISTS)
                    email.isBlank() -> setError(SigninContract.Error.WRONG_EMAIL)
                    password.isBlank() -> setError(SigninContract.Error.WRONG_PASSWORD)
                    password != password2 -> setError(SigninContract.Error.PASSWORDS_NOT_MATCH)
                    else -> {
                        val user = User(login = login, email = email, password = password)
                        saveUser(user)
                        showUser(user)
                    }
                }
            }.invoke()
        }
    }

    private fun saveUser(user: User) {
        users[user.login] = user
    }


    override fun onCancel() {
        view?.showLogin()
    }

    override fun onAttach(view: BaseContract.View) {
        this.view = view as SigninContract.View

        view.setState(BaseContract.ViewState.LOADING)
        delayedRun { view.setState(BaseContract.ViewState.IDLE) }.invoke()
    }

    override fun onDetach() {
        view = null
    }
}
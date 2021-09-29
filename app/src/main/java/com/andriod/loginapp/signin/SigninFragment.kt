package com.andriod.loginapp.signin

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.andriod.loginapp.R
import com.andriod.loginapp.base.BaseContract
import com.andriod.loginapp.databinding.FragmentSigninBinding
import com.andriod.loginapp.entity.User

class SigninFragment : Fragment(), SigninContract.View {
    private var _binding: FragmentSigninBinding? = null
    private val binding: FragmentSigninBinding get() = _binding!!

    private val presenter: SigninContract.Presenter by lazy { SigninPresenter() }
    private val contract by lazy { requireActivity() as Contract }
    private val handler by lazy { Handler(Looper.getMainLooper()) }

    private val changeStateRunnable = { state: BaseContract.ViewState ->
        {
            binding.root.children.forEach { it.isVisible = false }

            when (state) {
                BaseContract.ViewState.IDLE -> binding.container.isVisible = true
                BaseContract.ViewState.LOADING -> binding.progressBar.isVisible = true
                BaseContract.ViewState.COMPLETE -> binding.container.isVisible = true
            }
        }
    }
    private val processErrorRunnable = { error: SigninContract.Error ->
        {
            when (error) {
                SigninContract.Error.WRONG_LOGIN -> binding.loginEditText.error =
                    getString(R.string.error_wrong_login)
                SigninContract.Error.WRONG_EMAIL -> binding.emailEditText.error =
                    getString(R.string.error_wrong_email)
                SigninContract.Error.PASSWORDS_NOT_MATCH -> binding.passwordRepeatEditText.error =
                    getString(R.string.error_passwords_not_match)
                SigninContract.Error.LOGIN_EXISTS -> binding.loginEditText.error =
                    getString(R.string.error_login_exists)
                SigninContract.Error.WRONG_PASSWORD -> binding.passwordEditText.error =
                    getString(R.string.error_wrong_password)
            }
        }
    }

    interface Contract {
        fun showLogin()
        fun showUser(user: User)
    }

    override fun setError(error: SigninContract.Error) {
        handler.post(processErrorRunnable(error))
    }

    override fun showUser(user: User) {
        contract.showUser(user)
    }

    override fun showLogin() {
        contract.showLogin()
    }

    override fun setState(state: BaseContract.ViewState) {
        handler.post(changeStateRunnable(state))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSigninBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onAttach(this)


        binding.apply {
            cancelButton.setOnClickListener {
                presenter.onCancel()
            }

            registerButton.setOnClickListener {
                presenter.onSave(
                    loginEditText.text.toString(),
                    emailEditText.text.toString(),
                    passwordEditText.text.toString(),
                    passwordRepeatEditText.text.toString()
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
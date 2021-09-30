package com.andriod.loginapp.login

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.andriod.loginapp.LoginApplication
import com.andriod.loginapp.R
import com.andriod.loginapp.base.BaseContract
import com.andriod.loginapp.databinding.FragmentLoginBinding
import com.andriod.loginapp.entity.User
import com.google.android.material.snackbar.Snackbar


class LoginFragment : Fragment(), LoginContract.View {
    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding get() = _binding!!

    private val presenter: LoginContract.Presenter by lazy { LoginPresenter(loginApplication.dataProvider) }
    private val contract by lazy { requireActivity() as Contract }
    private val loginApplication by lazy { requireActivity().application as LoginApplication }
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
    private val processErrorRunnable = { error: LoginContract.Error ->
        {
            when (error) {
                LoginContract.Error.WRONG_PASSWORD ->
                    showWrongPassword()
                LoginContract.Error.NO_LOGIN ->
                    showWrongLogin()
            }
        }
    }

    interface Contract {
        fun showSignIn()
        fun showUser(user: User)
        fun hideKeyboard(view: View)
    }

    override fun setState(state: BaseContract.ViewState) {
        handler.post(changeStateRunnable(state))
    }

    override fun setError(error: LoginContract.Error) {
        contract.hideKeyboard(binding.root)
        handler.post(processErrorRunnable(error))
    }

    private fun showWrongLogin() {
        Toast.makeText(requireContext(), getString(R.string.missing_login), Toast.LENGTH_SHORT)
            .show()
    }

    private fun showWrongPassword() {
        Snackbar.make(
            binding.root,
            getString(R.string.Error_password),
            Snackbar.LENGTH_LONG
        ).setAction(getString(R.string.forget_password_action)) {
            presenter.onForgetPassword(binding.loginEditText.text.toString())
        }.show()
    }

    override fun showSignIn() {
        contract.showSignIn()
    }

    override fun showForget(user: User) {
        //todo
    }

    override fun showUser(user: User) {
        contract.showUser(user)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onAttach(this)

        binding.loginButton.setOnClickListener {
            presenter.onLogin(
                binding.loginEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }

        binding.signinButton.setOnClickListener {
            presenter.onSignIn()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
        _binding = null
    }
}
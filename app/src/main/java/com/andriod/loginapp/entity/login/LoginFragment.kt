package com.andriod.loginapp.entity.login

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
import com.andriod.loginapp.R
import com.andriod.loginapp.databinding.FragmentLoginBinding
import com.andriod.loginapp.entity.User
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment(), LoginContract.View {
    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding get() = _binding!!

    private val presenter: LoginContract.Presenter by lazy { LoginPresenter() }
    private val contract by lazy { requireActivity() as Contract }
    private val handler by lazy { Handler(Looper.getMainLooper()) }
    private val changeStateRunnable =
        { state: LoginContract.ViewState ->
            {
                binding.root.children.forEach { it.isVisible = false }

                when (state) {
                    LoginContract.ViewState.IDLE -> binding.container.isVisible = true
                    LoginContract.ViewState.LOADING -> binding.progressBar.isVisible = true
                    LoginContract.ViewState.COMPLETE -> binding.container.isVisible = true
                }
            }
        }

    interface Contract {
        fun showSignIn()
        fun showForget(user: User)
        fun showUser(user: User)
    }

    override fun setState(state: LoginContract.ViewState) {
        handler.post(changeStateRunnable(state))
    }

    override fun setError(error: LoginContract.Error) = when (error) {
        LoginContract.Error.WRONG_PASSWORD ->
            Snackbar.make(
                binding.root,
                getString(R.string.Error_password),
                Snackbar.LENGTH_INDEFINITE
            ).setAction(getString(R.string.forget_password_action)) {
                presenter.onForgetPassword(binding.loginEditText.text.toString())
            }.show()
        LoginContract.Error.NO_LOGIN ->
            Toast.makeText(requireContext(), getString(R.string.missing_login), Toast.LENGTH_SHORT)
                .show()
    }

    override fun showSignIn() {
        contract.showSignIn()
    }

    override fun showForget(user: User) {
        contract.showForget(user)
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
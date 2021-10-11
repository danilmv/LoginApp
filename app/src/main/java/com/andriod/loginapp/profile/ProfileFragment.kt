package com.andriod.loginapp.profile

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.andriod.loginapp.base.BaseContract
import com.andriod.loginapp.databinding.FragmentProfileBinding
import com.andriod.loginapp.entity.User

class ProfileFragment : Fragment(), ProfileContract.View {
    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding get() = _binding!!

    private val presenter: ProfileContract.Presenter by lazy { ProfilePresenter() }
    private val contract by lazy { requireActivity() as Contract }
    private val handler by lazy { Handler(Looper.getMainLooper()) }

    private lateinit var user: User

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

    override fun showLogin() {
        contract.showLogin()
    }

    override fun setState(state: BaseContract.ViewState) {
        handler.post(changeStateRunnable(state))
    }

    interface Contract {
        fun showLogin()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onAttach(this)

        user = arguments?.getParcelable<User>(EXTRA_USER_KEY)!!

        binding.loginEditText.setText(user.login)
        binding.emailEditText.setText(user.email)

        binding.logoutButton.setOnClickListener{
            presenter.onLogout()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
        _binding = null
    }

    companion object {
        const val EXTRA_USER_KEY = "user"

        fun newInstance(user: User) = ProfileFragment().apply {
            arguments = Bundle().apply {
                putParcelable(EXTRA_USER_KEY, user)
            }
        }
    }

}
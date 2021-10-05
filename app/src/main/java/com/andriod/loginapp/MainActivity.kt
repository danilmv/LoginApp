package com.andriod.loginapp

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.andriod.loginapp.databinding.ActivityMainBinding
import com.andriod.loginapp.entity.User
import com.andriod.loginapp.login.LoginFragment
import com.andriod.loginapp.profile.ProfileFragment
import com.andriod.loginapp.signin.SigninFragment

class MainActivity : AppCompatActivity(),
    LoginFragment.Contract,
    SigninFragment.Contract,
    ProfileFragment.Contract {

    private lateinit var binding: ActivityMainBinding
    private val inputMethodManager by lazy { getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) showLogin()
    }

    override fun showSignIn() {
        hideKeyboard(binding.container)
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, SigninFragment())
            .commit()
    }

    override fun showUser(user: User) {
        hideKeyboard(binding.container)
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, ProfileFragment.newInstance(user))
            .commit()
    }

    override fun hideKeyboard(view: View) {
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun showLogin() {
        hideKeyboard(binding.container)
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, LoginFragment())
            .commit()
    }
}
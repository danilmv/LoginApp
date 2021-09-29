package com.andriod.loginapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.andriod.loginapp.databinding.ActivityMainBinding
import com.andriod.loginapp.entity.User
import com.andriod.loginapp.login.LoginFragment
import com.andriod.loginapp.signin.SigninFragment

class MainActivity : AppCompatActivity(), LoginFragment.Contract, SigninFragment.Contract {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLogin()
    }

    override fun showSignIn() {
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, SigninFragment())
            .commit()
    }

    override fun showForget(user: User) {
    }

    override fun showUser(user: User) {
    }

    override fun showLogin() {
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, LoginFragment())
            .commit()
    }
}
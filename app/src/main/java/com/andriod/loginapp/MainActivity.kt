package com.andriod.loginapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.andriod.loginapp.databinding.ActivityMainBinding
import com.andriod.loginapp.entity.User
import com.andriod.loginapp.login.LoginFragment

class MainActivity : AppCompatActivity(), LoginFragment.Contract {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLogin()
    }

    override fun showSignIn() {
    }

    override fun showForget(user: User) {
    }

    override fun showUser(user: User) {
    }

    fun showLogin(){
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, LoginFragment())
            .commit()
    }
}
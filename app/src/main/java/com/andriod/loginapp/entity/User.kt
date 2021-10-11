package com.andriod.loginapp.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class User(
    val id: String = UUID.randomUUID().toString(),
    val login: String,
    val email: String,
    val password: String,
) : Parcelable

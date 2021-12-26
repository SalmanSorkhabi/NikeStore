package com.example.nikestore.data.repo.user

import android.content.SharedPreferences
import com.example.nikestore.data.dataclass.MessageResponse
import com.example.nikestore.data.dataclass.TokenContainer
import com.example.nikestore.data.dataclass.TokenResponse
import io.reactivex.Single

const val ACCESS_TOKEN = "access_token"
const val REFRESH_TOKEN = "refresh_token"

class UserLocalDataSource(val sharedPreferences: SharedPreferences): UserDataSource {
    override fun login(username: String, password: String): Single<TokenResponse> {
        TODO("Not yet implemented")
    }

    override fun signUp(username: String, password: String): Single<MessageResponse> {
        TODO("Not yet implemented")
    }

    override fun loadToken() {
        TokenContainer.update(
            sharedPreferences.getString(ACCESS_TOKEN,null),
            sharedPreferences.getString(REFRESH_TOKEN,null))
    }

    override fun saveToken(token: String, refreshToken: String) {
        sharedPreferences.edit().apply {
            putString(ACCESS_TOKEN,token)
            putString(REFRESH_TOKEN,refreshToken)
        }.apply()
    }

    override fun saveUserName(userName: String) {
        sharedPreferences.edit().apply {
            putString("username",userName)
        }.apply()
    }

    override fun getUserName(): String = sharedPreferences.getString("username","")?:""


    override fun signOut() = sharedPreferences.edit().apply { clear() }.apply()
}
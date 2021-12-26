package com.example.nikestore.data.repo.user

import com.example.nikestore.data.dataclass.MessageResponse
import com.example.nikestore.data.dataclass.TokenResponse
import io.reactivex.Single

interface UserDataSource {

    fun login(username:String,password:String):Single<TokenResponse>
    fun signUp(username:String,password:String):Single<MessageResponse>
    fun loadToken()
    fun saveToken(token:String,refreshToken:String)
    fun saveUserName(userName:String)
    fun getUserName():String
    fun signOut()
}
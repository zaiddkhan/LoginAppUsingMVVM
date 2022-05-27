package com.example.loginappusingmvvm.repository

import com.example.loginappusingmvvm.data.UserPreferences
import com.example.loginappusingmvvm.network.AuthApi
import java.util.*

class AuthRepository(
    private val api:AuthApi,
    private val preferences:UserPreferences
) : BaseRepository(){
   suspend fun login(email:String,password:String)= safeApiCall {
        api.login(email,password)
    }

    suspend fun saveAuthToken(token:String){
        preferences.saveAuthToken(token)
    }
}
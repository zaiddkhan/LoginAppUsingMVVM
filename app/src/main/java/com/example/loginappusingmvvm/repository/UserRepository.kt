package com.example.loginappusingmvvm.repository

import com.example.loginappusingmvvm.data.UserPreferences
import com.example.loginappusingmvvm.network.AuthApi
import com.example.loginappusingmvvm.network.UserApi
import java.util.*

class UserRepository(
    private val api: UserApi,
    private val preferences:UserPreferences
) : BaseRepository(){
  suspend fun getUser() = safeApiCall {
      api.getUser()
  }
}
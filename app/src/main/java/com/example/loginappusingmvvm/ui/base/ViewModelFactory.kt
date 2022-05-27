package com.example.loginappusingmvvm.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.loginappusingmvvm.home.HomeViewModel
import com.example.loginappusingmvvm.repository.AuthRepository
import com.example.loginappusingmvvm.repository.BaseRepository
import com.example.loginappusingmvvm.repository.UserRepository
import com.example.loginappusingmvvm.ui.auth.AuthViewModel

class ViewModelFactory
    (private val repository : BaseRepository):ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
      return   when{
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                AuthViewModel(repository as AuthRepository) as T
            }
          modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
              HomeViewModel(repository as UserRepository) as T
          }
            else -> {
                throw IllegalArgumentException("View model cass not fount")
            }
        }
    }
}
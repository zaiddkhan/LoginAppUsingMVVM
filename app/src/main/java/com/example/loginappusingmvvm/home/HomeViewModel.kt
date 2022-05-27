package com.example.loginappusingmvvm.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginappusingmvvm.network.Resource
import com.example.loginappusingmvvm.repository.UserRepository
import com.example.loginappusingmvvm.responses.LoginResponse
import com.example.loginappusingmvvm.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: UserRepository) :BaseViewModel(repository){
    private val _user: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val users:LiveData<Resource<LoginResponse>>
    get() = _user

    fun getUser() = viewModelScope.launch {
        _user.value = Resource.Loading
        _user.value = repository.getUser()
    }
}
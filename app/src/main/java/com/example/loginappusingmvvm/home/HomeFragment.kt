package com.example.loginappusingmvvm.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.loginappusingmvvm.R
import com.example.loginappusingmvvm.databinding.FragmentHomeBinding
import com.example.loginappusingmvvm.network.Resource
import com.example.loginappusingmvvm.network.UserApi
import com.example.loginappusingmvvm.repository.UserRepository
import com.example.loginappusingmvvm.responses.User
import com.example.loginappusingmvvm.ui.base.BaseFragment
import com.example.loginappusingmvvm.utils.visible
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.*


class HomeFragment :BaseFragment<HomeViewModel,FragmentHomeBinding,UserRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.visible(false)
        viewModel.getUser()
        viewModel.users.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when(it){
                is Resource.Success -> {
                    binding.progressBar.visible(false)
                    updateUI(it.value.user)
                }
                is Resource.Loading -> {
                    binding.progressBar.visible(true)


                }
            }
        })


    }
    override fun getViewModel() = HomeViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater,container,false)

    override fun getRepository(): UserRepository {

        val token = runBlocking {   userPreferences.authToken.first()}
        val api = remoteDataSource.buildApi(UserApi::class.java, token )
        return UserRepository(api,userPreferences)
    }
    private fun updateUI(user: User){

    }
}
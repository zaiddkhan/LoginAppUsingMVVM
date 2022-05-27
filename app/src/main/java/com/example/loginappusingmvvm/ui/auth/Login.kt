package com.example.loginappusingmvvm.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.loginappusingmvvm.databinding.FragmentLoginBinding
import com.example.loginappusingmvvm.home.HomeActivity
import com.example.loginappusingmvvm.network.AuthApi
import com.example.loginappusingmvvm.network.Resource
import com.example.loginappusingmvvm.repository.AuthRepository
import com.example.loginappusingmvvm.ui.base.BaseFragment
import com.example.loginappusingmvvm.utils.enable
import com.example.loginappusingmvvm.utils.handleApiErrors
import com.example.loginappusingmvvm.utils.startNewActivity
import com.example.loginappusingmvvm.utils.visible
import kotlinx.coroutines.launch


class Login : BaseFragment<AuthViewModel,FragmentLoginBinding,AuthRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        binding.progressBar.visible(false)
        binding.btnSubmit.enable(false)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when(it){
                is Resource.Success -> {
                    lifecycleScope.launch {
                        viewModel.saveAuthToken(it.value.user.access_token)
                        requireActivity().startNewActivity(HomeActivity::class.java)
                    }

                }
                is Resource.Failure -> {
                    handleApiErrors(it){login()}
                }
            }
        })

        binding.loginEmail.addTextChangedListener{
            val email  = binding.loginEmail.text.toString().trim()
            binding.btnSubmit.enable(email.isNotEmpty() && it.toString().isNotEmpty())

        }
        binding.btnSubmit.setOnClickListener {
           login()
        }
    }
    private fun login(){
        val email = binding.loginEmail.text.toString().trim()
        val password = binding.loginPassword.text.toString().trim()
        viewModel.login(email,password)
    }
    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentLoginBinding.inflate(inflater,container,false)

    override fun getRepository() = AuthRepository(remoteDataSource.buildApi(AuthApi::class.java), userPreferences)
}

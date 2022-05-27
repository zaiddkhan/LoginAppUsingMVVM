package com.example.loginappusingmvvm.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.loginappusingmvvm.data.UserPreferences
import com.example.loginappusingmvvm.network.RemoteDataSource
import com.example.loginappusingmvvm.network.UserApi
import com.example.loginappusingmvvm.repository.AuthRepository
import com.example.loginappusingmvvm.repository.BaseRepository
import com.example.loginappusingmvvm.responses.User
import com.example.loginappusingmvvm.ui.auth.AuthActivity
import com.example.loginappusingmvvm.utils.startNewActivity
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

abstract class BaseFragment<vm:BaseViewModel,vb: ViewBinding,br:BaseRepository> :Fragment(){


    protected lateinit var userPreferences : UserPreferences
    protected lateinit var binding: vb
    protected lateinit var viewModel:vm
    protected val remoteDataSource = RemoteDataSource()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getFragmentBinding(inflater,container)
        val factory = ViewModelFactory(getRepository())
        userPreferences = UserPreferences(requireContext())
        viewModel = ViewModelProvider(this,factory).get(getViewModel())
        lifecycleScope.launch {
            userPreferences.authToken.first()
        }
        return binding.root
    }

    fun logout() = lifecycleScope.launch {
        val authToken = userPreferences.authToken.first()
        val api = remoteDataSource.buildApi(UserApi::class.java)
        viewModel.logout(api)
        userPreferences.clear()
        requireActivity().startNewActivity(AuthActivity::class.java)
    }

    abstract fun getViewModel():Class<vm>

    abstract fun getFragmentBinding(inflater: LayoutInflater,container: ViewGroup?):vb

    abstract fun getRepository():br

}
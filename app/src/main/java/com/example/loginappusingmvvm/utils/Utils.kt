package com.example.loginappusingmvvm.utils

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import com.example.loginappusingmvvm.network.Resource
import com.example.loginappusingmvvm.ui.auth.Login
import com.example.loginappusingmvvm.ui.base.BaseFragment
import com.google.android.material.snackbar.Snackbar

fun<A:Activity> Activity.startNewActivity(activity: Class<A>){
    Intent(this,activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun View.visible(isVisible:Boolean){
    visibility = if(isVisible) View.VISIBLE else View.GONE
}

fun View.enable(enabled:Boolean){
    isEnabled = enabled
    alpha = if(enabled) 1f else 0.5f

}


fun View.snackbar(message:String,action: (() -> Unit)? = null){
    val snackbar = Snackbar.make(this,message,Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction("Retry"){
            it()
        }
    }
    snackbar.show()
}


fun Fragment.handleApiErrors(
    failure: Resource.Failure,
    retry : (() -> Unit)? = null
){
    when{
        failure.isNetworkError -> {
            requireView().snackbar("Check your network")
        }
        failure.errorCode == 401 -> {
            if(this is Login){
                requireView().snackbar("You have entered incorrect email or password")
            }else{
                (this as BaseFragment<*,*,*>).logout()
            }
        }
        else -> {
            val error = failure.errorBody.toString()
            requireView().snackbar(error)
        }
    }

}
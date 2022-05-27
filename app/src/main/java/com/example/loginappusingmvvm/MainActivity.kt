package com.example.loginappusingmvvm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.example.loginappusingmvvm.data.UserPreferences
import com.example.loginappusingmvvm.ui.auth.AuthActivity
import com.example.loginappusingmvvm.utils.startNewActivity

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val userPreferences = UserPreferences(this)
        userPreferences.authToken.asLiveData().observe(this, Observer {
            val activity =  if(it == null)AuthActivity::class.java else MainActivity::class.java
            startActivity(Intent(this,AuthActivity::class.java))
            startNewActivity(activity)
        })
    }
}
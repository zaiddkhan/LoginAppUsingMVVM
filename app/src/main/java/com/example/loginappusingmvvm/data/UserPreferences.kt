package com.example.loginappusingmvvm.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences (val context:Context){
    private val applicationContext = context.applicationContext

        private val Context.dataStore:DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(
            name = "my_data_store"
        )

    val authToken: Flow<String?>
    get() = context.dataStore.data.map {
        preferences ->
        preferences[KEY_AUTH]
    }

    suspend fun clear(){
        context.dataStore.edit {
            it.clear()
        }
    }

    suspend fun saveAuthToken(authToken :String){
        context.dataStore.edit {
            it[KEY_AUTH] = authToken
        }

    }
    companion object{
        private val KEY_AUTH = stringPreferencesKey("key_auth")
    }
}
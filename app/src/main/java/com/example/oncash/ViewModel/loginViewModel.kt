package com.example.oncash.ViewModel

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.util.Log
import androidx.core.content.edit
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oncash.Component.get_UserInfo_UseCase
import com.example.oncash.DataType.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.properties.Delegates

class loginViewModel:ViewModel() {
    private  var userData:MutableLiveData<UserData> = MutableLiveData()

    fun addUser(userNumber : Long ) {

        viewModelScope.launch {
            withContext(Dispatchers.Main) {

                userData.value = get_UserInfo_UseCase().loginManager(userNumber)

            }
        }
    }

    fun getUserData(): MutableLiveData<UserData>{
        return userData
    }



}
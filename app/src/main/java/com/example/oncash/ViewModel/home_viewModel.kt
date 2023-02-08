package com.example.oncash.ViewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oncash.Component.UserDataStoreUseCase
import com.example.oncash.DataType.Offer
import com.example.oncash.DataType.userDataa
import com.example.oncash.Repository.Offer_FIrebase
import com.example.oncash.Repository.UserInfo_Airtable_Repo
import com.example.oncash.Repository.offer_AirtableDatabase
import io.ktor.http.*
import kotlinx.coroutines.*

@OptIn(DelicateCoroutinesApi::class)
public class home_viewModel() : ViewModel() {

    private val offerList : MutableLiveData<ArrayList<Offer>> = MutableLiveData()
    private val wallet : MutableLiveData<Int> = MutableLiveData(0)
    private val userData : MutableLiveData<userDataa> = MutableLiveData()


    fun getOfferList() : MutableLiveData<ArrayList<Offer>>{
        viewModelScope.launch{
            offerList.postValue( Offer_FIrebase().getData() )
            offer_AirtableDatabase().getData()
        }
        return offerList
    }

  fun getWallet(userRecordId :String) {
        viewModelScope.launch {
               Log.i("recordID" , userRecordId)
                wallet.value = UserInfo_Airtable_Repo().getWallet(
                        userRecordId
                    )
                }
    }


    fun getUserData(context:Context) {
        viewModelScope.launch {
          userData.postValue( userDataa(UserDataStoreUseCase().retrieveUserRecordId(context) ,  UserDataStoreUseCase().retrieveUserNumber(context)) )
        }
    }

    fun getuserData():MutableLiveData<userDataa>{
        return userData
    }

    fun getWalletPrice():MutableLiveData<Int>{
        return wallet
    }

}
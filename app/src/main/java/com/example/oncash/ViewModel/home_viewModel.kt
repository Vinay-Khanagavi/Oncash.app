package com.example.oncash.ViewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oncash.Component.UserDataStoreUseCase
import com.example.oncash.Component.offerHistory_component
import com.example.oncash.Component.sortingComponent
import com.example.oncash.DataType.Offer
import com.example.oncash.DataType.OfferList
import com.example.oncash.DataType.SerializedDataType.OfferHistory.OfferHistoryRecord
import com.example.oncash.DataType.userData
import com.example.oncash.DataType.userDataa
import com.example.oncash.Repository.Offer_FIrebase
import com.example.oncash.Repository.UserInfo_Airtable_Repo
import com.example.oncash.Repository.offer_AirtableDatabase
import io.ktor.http.*
import kotlinx.coroutines.*

@OptIn(DelicateCoroutinesApi::class)
public class home_viewModel() : ViewModel() {

    private val wallet : MutableLiveData<Int> = MutableLiveData(0)
    private val userData : MutableLiveData<userDataa> = MutableLiveData()


// weekly offer viewmodel
    private val offerList : MutableLiveData<OfferList> = MutableLiveData()

    fun getOfferList() : MutableLiveData<OfferList> {
        viewModelScope.launch {
            offerList.postValue(sortingComponent().sortOfferList(Offer_FIrebase().getData()))
            offer_AirtableDatabase().getData()
        }
        return offerList
    }
//offer history viewmodel
private val offerhistoryList : MutableLiveData<ArrayList<OfferHistoryRecord>> = MutableLiveData()
    fun getOffersHistory(userId:String){
        viewModelScope.launch {
            offerhistoryList.postValue(offerHistory_component().getOfferHIstory(userId = userId))
        }
    }

    fun getOfferHistoryList():MutableLiveData<ArrayList<OfferHistoryRecord>>{
        return offerhistoryList
    }

//home viewmodel

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
          userData.postValue( userData(UserDataStoreUseCase().retrieveUserRecordId(context) ,  UserDataStoreUseCase().retrieveUserNumber(context)) )
        }
    }

    fun getuserData():MutableLiveData<userData>{
        return userData
    }

    fun getWalletPrice():MutableLiveData<Int>{
        return wallet
    }

}
package com.example.oncash.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.oncash.Component.get_UserInfo_UseCase
import com.example.oncash.Component.withdraw_usecase
import com.example.oncash.DataType.withdrawalTransaction
import com.example.oncash.DataType.withdrawalsuccess
import com.example.oncash.Repository.UserInfo_Airtable_Repo
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class wallet_viewModel:ViewModel() {
    private val withdrawalRequest :MutableLiveData<withdrawalsuccess> = MutableLiveData()
    private val wallet : MutableLiveData<Int> = MutableLiveData(0)
    private val withdrawalTransaction : MutableLiveData<ArrayList<withdrawalTransaction>> = MutableLiveData()
    fun withdrawRequest(userNumber: Long, requestAmount: Int, walletBalance: Int , userRecordId :String)  {
        viewModelScope.launch {
            withdrawalRequest.value = withdraw_usecase().withdrawRequest(userNumber, requestAmount, walletBalance, userRecordId)
        }
    }

    fun withdrawalTransaction(userNumber :Long){
        viewModelScope.launch {
          withdrawalTransaction.value =   get_UserInfo_UseCase().getuserWithdrwalHistory(userNumber )

        }

    }
    fun getWithdrawalTransaction():MutableLiveData<ArrayList<withdrawalTransaction>>{
        return withdrawalTransaction

    }
    fun getWithdrawalRequest(): MutableLiveData<withdrawalsuccess>{
        return withdrawalRequest
    }

    fun getWallet(userRecordId :String) {
        viewModelScope.launch {
            Log.i("recordID" , userRecordId)
            wallet.value = UserInfo_Airtable_Repo().getWallet(
                userRecordId
            )
        }
    }
    fun getWalletPrice():MutableLiveData<Int>{
        return wallet
    }



}
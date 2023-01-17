package com.example.oncash.Repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.oncash.DataType.Offer
import kotlinx.coroutines.tasks.await

import com.google.firebase.database.*
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.withContext

class Offer_FIrebase  {

    suspend fun getData() : ArrayList<Offer>  = withContext(Dispatchers.IO){
       // FirebaseApp.initializeApp(context)
        val data : DatabaseReference = FirebaseDatabase.getInstance().getReference("Offers")
        val offerList : MutableLiveData<ArrayList<Offer>> = MutableLiveData()
        val response = ArrayList<Offer>()
        try {
            data.get().await().children.map { snapShot ->
                response.add( snapShot.getValue(Offer::class.java)!! )
            }
        } catch (exception: Exception) {

        }
        Log.i("fbdata", response?.size.toString())

        return@withContext response

    }

}
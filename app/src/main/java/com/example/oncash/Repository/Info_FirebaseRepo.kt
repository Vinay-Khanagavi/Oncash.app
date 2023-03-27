package com.example.oncash.Repository

import android.util.Log
import com.example.oncash.DataType.Instruction
import com.example.oncash.DataType.Offer
import com.example.oncash.DataType.Offer_Information
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class Info_FirebaseRepo {

   suspend fun getInstructionList( offerId :String) :ArrayList<Instruction>  = withContext(Dispatchers.IO){
        val response : ArrayList<Instruction> = ArrayList()
        val data : DatabaseReference = FirebaseDatabase.getInstance().getReference("Offers").child(offerId).child("Instructions")
       try {
           data.get().await().children.map { snapShot ->
               val instruction = snapShot.value as String
               val serialNumber = snapShot.key as String
               response.add(Instruction(instruction , serialNumber))
           }
       } catch (_: Exception) {

       }

       return@withContext response


    }




}
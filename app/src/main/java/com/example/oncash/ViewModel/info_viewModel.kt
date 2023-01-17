package com.example.oncash.ViewModel

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.oncash.Repository.Info_FirebaseRepo
import com.example.oncash.DataType.Instruction
import com.example.oncash.DataType.Offer_Information
import kotlinx.coroutines.launch

class info_viewModel(): ViewModel() {

    val InstructionsList : MutableLiveData<ArrayList<Instruction>> = MutableLiveData()

    fun getInstrutionList(offerId: String) : MutableLiveData<ArrayList<Instruction>>{
        viewModelScope.launch{
            InstructionsList.value = Info_FirebaseRepo().getInstructionList(offerId)
        }

        return InstructionsList
    }
}
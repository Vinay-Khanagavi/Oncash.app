package com.example.oncash.Component

import androidx.recyclerview.widget.DiffUtil
import com.example.oncash.DataType.Offer

class Diffutil(val oldList : ArrayList<Offer> , val newList : ArrayList<Offer>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {

        return oldList.size
    }

    override fun getNewListSize(): Int {

      return  newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return if(oldList.get(oldItemPosition).Image == newList.get(newItemPosition).Image) true
            else false
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return if(oldList.get(oldItemPosition).Image == newList.get(newItemPosition).Image) true
        else false
    }
}
package com.example.oncash.Component

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.oncash.R
import com.example.oncash.DataType.Instruction
import com.example.oncash.DataType.withdrawalTransaction


class withdrawalTransaction_RecylerViewAdapter : RecyclerView.Adapter<withdrawalTransaction_RecylerViewAdapter.viewholder>() {
    var withdrawalTransactionList : ArrayList<withdrawalTransaction> = ArrayList()

    var context : Context?=null
    class viewholder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val date : TextView
        val amount :TextView
        val status :TextView

        init {
            amount = itemView.findViewById(R.id.withdrawalTransactionAmount)
             date = itemView.findViewById(R.id.withdrawalTransactionDate)
            status = itemView.findViewById(R.id.withdrawalTransactionStatus)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        context = parent.context

        val v = LayoutInflater.from(parent.context).inflate(R.layout.withdraw_transaction_listview,parent,false)


        return viewholder(v)



    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: viewholder, position: Int) {

        holder.date.text= withdrawalTransactionList.get(position).Date
        holder.amount.text = withdrawalTransactionList.get(position).WithdrawalAmount
        holder.status.text = withdrawalTransactionList.get(position).Status
    }

    override fun getItemCount(): Int {

        return withdrawalTransactionList.size

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list :ArrayList<withdrawalTransaction>){
        this.withdrawalTransactionList.clear()
        this.withdrawalTransactionList.addAll(list)
        notifyDataSetChanged()
    }
}
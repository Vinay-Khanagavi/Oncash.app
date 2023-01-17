package com.example.oncash.View

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oncash.Component.withdrawalTransaction_RecylerViewAdapter
import com.example.oncash.DataType.withdrawalTransaction
import com.example.oncash.ViewModel.wallet_viewModel
import com.example.oncash.databinding.ActivityWalletBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Wallet : AppCompatActivity() {
    private lateinit var binding: ActivityWalletBinding
    private var userNumber: Long = 0
    private var userRecordId: String? = null
    private val viewModel: wallet_viewModel by viewModels()
    private val adapter = withdrawalTransaction_RecylerViewAdapter()
    private var withdrawalList :ArrayList<withdrawalTransaction> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalletBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var walletBalance = intent.getStringExtra("walletBalance")?.toInt()
        val userRecordId = intent.getStringExtra("userRecordId")
        userNumber  = intent.getStringExtra("userNumber")?.toLong()!!
        binding.walletBalanceWallet.text = walletBalance.toString()
        Log.i("USERR", userNumber.toString() )
        binding.withdrawalTransaction.adapter = adapter
        binding.withdrawalTransaction.layoutManager = LinearLayoutManager(this , LinearLayoutManager.VERTICAL ,false)

        lifecycleScope.launch { getTransaction() }

        binding.withdrawButton.setOnClickListener {
            val requestAmount = binding.withdrawRequestedAmount.text.toString()
            if (requestAmount.isNotEmpty()) {
                if (walletBalance!!.toInt() >= requestAmount.toInt()) {
                    if (requestAmount.toInt() > 20) {

                            viewModel.withdrawRequest(
                                userNumber,
                                requestAmount.toInt(),
                                walletBalance!!,
                                userRecordId!!
                            )
                                viewModel.getWithdrawalRequest().observe(this, Observer { status ->
                                if (status.response.contains("200")) {
                                    //   viewModel.getWallet(userRecordId)
                                    //  viewModel.getWalletPrice().observe(this, Observer { wallet ->

                                    //   walletBalance = wallet
                                    walletBalance =
                                        walletBalance!! - status.withdrawalTransaction.WithdrawalAmount.toInt()
                                    binding.walletBalanceWallet.text = walletBalance.toString()
                                    binding.withdrawRequestedAmount.editableText.clear()

                                    withdrawalList.add(status.withdrawalTransaction)
                                    adapter.updateList(withdrawalList)
                                    Snackbar.make(
                                        binding.root,
                                        "Withdraw Successful",
                                        Snackbar.LENGTH_LONG
                                    ).show()

                                }

                            })

                    } else {
                        Snackbar.make(
                            binding.root,
                            "Requested Amount Should Be More Then 20 Rs",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Snackbar.make(
                        binding.root,
                        "Insufficient balance",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            } else {
                Snackbar.make(binding.root, "Please enter withdraw amount", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }


 private suspend fun getTransaction(){
     viewModel.withdrawalTransaction(userNumber)
     viewModel.getWithdrawalTransaction().observe(this , Observer { withdrawalTransaction ->
         withdrawalList = withdrawalTransaction
         adapter.updateList(withdrawalTransaction)
     })
 }

}
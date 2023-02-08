package com.example.oncash.View

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oncash.Component.Offer_RecylerViewAdapter
import com.example.oncash.DataType.userDataa
import com.example.oncash.R
import com.example.oncash.ViewModel.home_viewModel
import com.example.oncash.databinding.ActivityHomeBinding
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.launch

class Home : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    val homeViewmodel: home_viewModel by viewModels()
    private  var userData: userDataa = userDataa("",0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        lifecycleScope.launch {
            getUserData()
        }
        FirebaseApp.initializeApp(this);
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.walletcardview.setBackgroundResource(R.drawable.walletbg)
        val adapter = Offer_RecylerViewAdapter(userData)

        val offerRecylerview: RecyclerView = findViewById(R.id.offer_recylerview)
        offerRecylerview.adapter = adapter
        offerRecylerview.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        homeViewmodel.getOfferList().observe(this, Observer { OfferList ->
            if (OfferList != null) {
                adapter.updateList(OfferList)
            }
        })

        binding.walletTextView.setOnClickListener {
            startActivity(
                Intent(this, Wallet::class.java).putExtra(
                    "walletBalance",
                    binding.walletTextView.text
                ) .putExtra("userNumber", userData.userNumber.toString()).putExtra("userRecordId", userData.userRecordId))


        }

        binding.walletcardview.setOnClickListener {
            startActivity(
                Intent(this, Wallet::class.java).putExtra(
                    "walletBalance",
                    binding.walletTextView.text
                ) .putExtra("userNumber", userData.userNumber.toString()).putExtra("userRecordId", userData.userRecordId))


        }

    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            getUserData()
        }

    }

    fun getUserData() {
        homeViewmodel.getUserData(this)
        homeViewmodel.getuserData().observe(this, Observer { data ->
            userData = data!!

            homeViewmodel.getWallet(userData.userRecordId)
        })

        homeViewmodel.getWalletPrice().observe(this, Observer { wallet ->
            binding.walletTextView.text = wallet.toString()
        })
    }
    @Deprecated("Deprecated in Java", ReplaceWith("this.finish()"))
    override fun onBackPressed() {
        this.finish()
    }
}
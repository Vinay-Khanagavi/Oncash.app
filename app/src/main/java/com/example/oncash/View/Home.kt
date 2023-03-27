package com.example.oncash.View

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oncash.Component.Offer_RecylerViewAdapter
import com.example.oncash.DataType.Offer
import com.example.oncash.DataType.OfferList
import com.example.oncash.DataType.userData
import com.example.oncash.R
import com.example.oncash.ViewModel.home_viewModel
import com.example.oncash.databinding.ActivityHomeBinding
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.launch

class Home : AppCompatActivity() {
     lateinit var binding: ActivityHomeBinding
    val homeViewmodel: home_viewModel by viewModels()
    lateinit var OfferList : OfferList
    private  var userData: userData = userData("",0)

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


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigation.setOnItemSelectedListener {
            if(it.itemId == R.id.home)
            {
                Toast.makeText(this , navController.currentDestination!!.displayName.toString() , Toast.LENGTH_LONG).show()
                if(navController.currentDestination!!.id==R.id.monthlyOffers){
                    navController.navigate(R.id.action_monthlyOffers_to_weeklyOffers)
                }else{
                }
            }
            if (it.itemId == R.id.history) {
                if (navController.currentDestination!!.id == R.id.weeklyOffers) {
                    navController.navigate(R.id.action_weeklyOffers_to_monthlyOffers)
                }
            }
            true
        }



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
            homeViewmodel.getOfferList()
        }

    }

    fun getUserData() {
        homeViewmodel.getUserData(this)
        homeViewmodel.getuserData().observe(this, Observer { data ->
            userData = data!!
            homeViewmodel.getOffersHistory(data.userRecordId)
            homeViewmodel.getWallet(userData.userRecordId)
        })

        homeViewmodel.getWalletPrice().observe(this, Observer { wallet ->
            binding.walletTextView.text = wallet.toString()
        })
    }
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
       this.finish()
    }
}

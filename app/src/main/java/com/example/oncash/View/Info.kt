package com.example.oncash.View

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.oncash.Component.Instructions_RecylerViewAdapter
import com.example.oncash.Repository.offer_AirtableDatabase
import com.example.oncash.ViewModel.info_viewModel
import com.example.oncash.databinding.ActivityInfoBinding


class Info : AppCompatActivity() {
     lateinit var binding : ActivityInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButtonInfo .setOnClickListener {
            startActivity(Intent(this@Info,Home::class.java))

        }

        //Getting Data from the intent form home(Activity)
        val offerId : String? = intent.getStringExtra("OfferId")
        binding.offernameInfo.text = intent.getStringExtra("OfferName")
        binding.offerPrice.text = intent.getStringExtra("OfferPrice")
        Glide.with(this).load(intent.getStringExtra("OfferImage")).into(binding.offerImageInfo)
        var offer :String? = intent.getStringExtra("OfferLink")
        val subid :String? = intent.getStringExtra("subid")
        val subid2 :String? = intent.getStringExtra("subid2")
        val number :String? = intent.getStringExtra("number")
        val recordId :String? = intent.getStringExtra("recordId")
        val offerLink = "$offer?&$subid=$recordId&$subid2=$number/"


        //Initilizing the recylerview adapter
        val adapter = Instructions_RecylerViewAdapter()
        binding.instructionListInfo.adapter = adapter
        binding.instructionListInfo.layoutManager = LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false)


        //Observing the getInstructionList() in info_viewmodel (ie which gets the data from info_FirebaseRepo)
        val info_viewModel : info_viewModel by viewModels()

        info_viewModel.getInstrutionList(offerId!!).observe(this , Observer {
            if (it.isNotEmpty()){
                Toast.makeText(this , it.size.toString() , Toast.LENGTH_LONG).show()
                adapter.updateList(it)
            }
        })


        //Redirecting user to chrome after the event of button accurs
        binding.offerLinkButtonInfo.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(offerLink))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.setPackage("com.android.chrome")
            try {
                this.startActivity(intent)
            } catch (ex: ActivityNotFoundException) {
                // Chrome browser presumably not installed so allow user to choose instead
                intent.setPackage(null)
                this.startActivity(intent)
            }
        }

        offer_AirtableDatabase().getData()





    }
}
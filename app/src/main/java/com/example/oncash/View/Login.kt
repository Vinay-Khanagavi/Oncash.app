package com.example.oncash.View

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.oncash.Component.UserDataStoreUseCase
import com.example.oncash.ViewModel.loginViewModel
import com.example.oncash.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)


        var isUserLogin: Boolean? = null
        val viewModel: loginViewModel by viewModels()
        lifecycleScope.launch {
            isUserLogin = UserDataStoreUseCase().retrieveUser(this@Login)
            if (isUserLogin == true) {
                startActivity(Intent(this@Login, Home::class.java))
            }
        }

        setContentView(binding.root)



        binding.phoneButtonInput.setOnClickListener {
            val phone = binding.phoneInput.text.toString()

            if (phone.length == 10) {

                viewModel.addUser(phone.toLong())
                viewModel.getUserData().observe(this, Observer { userData ->

                    if (userData.isUserRegistered) {
                        lifecycleScope.launch {
                            Log.i("LoginData",userData.userRecordId.toString())
                           UserDataStoreUseCase().storeUser(
                                this@Login,
                                userData.isUserRegistered,
                                phone.toLong(),
                                userData.userRecordId
                            )
                           startActivity(Intent(this@Login, Home::class.java))


                        }
                    } else {
                        Snackbar.make(
                            binding.root,
                            "Error Registering , Please try again",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    Log.i(
                        "login Data",
                        userData.isUserRegistered.toString() + userData.userRecordId
                    )


                })


            } else {
                Snackbar.make(
                    binding.root,
                    "Please Enter Your Correct Number",
                    Snackbar.LENGTH_LONG
                ).show()
            }

        }


    }
}
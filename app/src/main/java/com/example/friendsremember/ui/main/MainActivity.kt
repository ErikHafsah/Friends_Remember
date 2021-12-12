package com.example.friendsremember.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.UserManager
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.friendsremember.R
import com.example.friendsremember.databinding.ActivityMainBinding
import com.example.friendsremember.ui.add.AddFriendsActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var userManager: UserManager
    var age = 0
    var nama = ""
    var gender = ""

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddData.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_add_data -> kotlin.run {
                val intentPindah = Intent(this@MainActivity, AddFriendsActivity::class.java)
                startActivity(intentPindah)
            }
        }
    }


}


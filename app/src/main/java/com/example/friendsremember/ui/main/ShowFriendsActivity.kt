package com.example.friendsremember.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.friendsremember.R
import com.example.friendsremember.ui.add.AddFriendsActivity

class ShowFriendsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "com.damrad.phonebook.id"
        const val EXTRA_NAME = "com.damrad.phonebook.name"
        const val EXTRA_SURNAME = "com.damrad.phonebook.surname"
        const val EXTRA_EMAIL = "com.damrad.phonebook.email"
        const val EXTRA_PHONE_NUMBER = "com.damrad.phonebook.phonenumber"
        const val EXTRA_GENDER = "com.damrad.phonebook.gender"
        const val EXTRA_EDIT = "com.damrad.phonebook.editing"
    }

    private val CALL_PERMISSION_REQUEST_CODE = 1

    val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_friends)
        setSupportActionBar(toolbar)

        val intent = intent

        val nameTV = findViewById<TextView>(R.id.nameTV)
        val surnameTV = findViewById<TextView>(R.id.surnameTV)
        val emailTV = findViewById<TextView>(R.id.emailTV)
        val phoneNrTV = findViewById<TextView>(R.id.phoneNrTV)
        val genderTV = findViewById<TextView>(R.id.genderTV)
        val imageShowIV = findViewById<ImageView>(R.id.imageShowIV)
        val editFab = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.editFab)
        val callContact = findViewById<Button>(R.id.callContact)

        if (intent.extras?.size() != 0 && intent.extras?.size()!! >= 5) {
            val id = intent.getIntExtra(EXTRA_ID, -1)
            val name = intent.getStringExtra(EXTRA_NAME)
            val surname = intent.getStringExtra(EXTRA_SURNAME)
            val email = intent.getStringExtra(EXTRA_EMAIL)
            val phoneNr = intent.getIntExtra(EXTRA_PHONE_NUMBER, -1)
            val gender = intent.getStringExtra(EXTRA_GENDER)

            if (id > -1 && phoneNr > -1) {
                toolbar.title = name
                nameTV.text = name
                surnameTV.text = surname
                emailTV.text = email
                phoneNrTV.text = phoneNr.toString()
                genderTV.text = gender

                if (gender == getString(R.string.Woman)) {
                    imageShowIV.setImageResource(R.drawable.woman)
                } else {
                    imageShowIV.setImageResource(R.drawable.man)
                }
            } else {
                Toast.makeText(this, getString(R.string.loading_error), Toast.LENGTH_LONG).show()
                finish()
            }
        }

        editFab.setOnClickListener {
            val editIntent = Intent(applicationContext, AddFriendsActivity::class.java)
            editIntent.putExtras(intent)
            editIntent.putExtra(EXTRA_EDIT, true)
            startActivity(editIntent)
            finish()
        }

        callContact.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_CALL)
            val phoneCall: String = "tel:" + phoneNrTV.text
            callIntent.data = Uri.parse(phoneCall)

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, getString(R.string.premissions_to_call), Toast.LENGTH_LONG).show()
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), CALL_PERMISSION_REQUEST_CODE)
            } else {
                startActivity(callIntent)
            }
        }
    }
}

package com.example.friendsremember.ui.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.friendsremember.R
import com.example.friendsremember.data.room.Contact
import com.example.friendsremember.ui.main.ContactViewModel
import com.example.friendsremember.ui.main.ShowFriendsActivity
import com.google.android.material.snackbar.Snackbar

class AddFriendsActivity : AppCompatActivity() {
    val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_friends)
        setSupportActionBar(toolbar)

        val intent = intent

        var editing = false

        var id: Int = -1
        val addTitleTV = findViewById<TextView>(R.id.add_title_tv)
        val addNameTV = findViewById<TextView>(R.id.addNameTV)
        val addSurnameTV = findViewById<TextView>(R.id.addSurnameTV)
        val addEmailTV = findViewById<TextView>(R.id.addEmailTV)
        val addPhoneTV = findViewById<TextView>(R.id.addPhoneTV)
        val chipWoman = findViewById<com.google.android.material.chip.Chip>(R.id.chipWoman)
        val chipMan = findViewById<com.google.android.material.chip.Chip>(R.id.chipMan)
        val cancelAddContact = findViewById<Button>(R.id.cancelAddContact)
        val saveAddContact = findViewById<Button>(R.id.saveAddContact)
        val AddContactLayout = findViewById<androidx.coordinatorlayout.widget.CoordinatorLayout>(R.id.AddContactLayout)


        if (intent.extras != null && intent.extras?.size() != 0 && intent.extras?.size()!! >= 5) {
            id = intent.getIntExtra(ShowFriendsActivity.EXTRA_ID, -1)
            val name = intent.getStringExtra(ShowFriendsActivity.EXTRA_NAME)
            val surname = intent.getStringExtra(ShowFriendsActivity.EXTRA_SURNAME)
            val email = intent.getStringExtra(ShowFriendsActivity.EXTRA_EMAIL)
            val phoneNr = intent.getIntExtra(ShowFriendsActivity.EXTRA_PHONE_NUMBER, -1)
            val gender = intent.getStringExtra(ShowFriendsActivity.EXTRA_GENDER)
            editing = intent.getBooleanExtra(ShowFriendsActivity.EXTRA_EDIT, false)

            if (id > -1 && phoneNr > -1) {
                addTitleTV.text = name
                addNameTV.text = name
                addSurnameTV.text = surname
                addEmailTV.text = email
                addPhoneTV.text = phoneNr.toString()

                if (gender == getString(R.string.Woman)) {
                    chipWoman.isChecked = true
                } else {
                    chipMan.isChecked = true
                }
            } else {
                Toast.makeText(this, getString(R.string.loading_error), Toast.LENGTH_LONG).show()
                finish()
            }
        }

        cancelAddContact.setOnClickListener { finish() }

        saveAddContact.setOnClickListener {
            if (addNameTV.text.trim().isEmpty()) {
                addNameTV.error = getString(R.string.cannot_be_empty)
            } else if (addPhoneTV.text.trim().isEmpty()) {
                addPhoneTV.error = getString(R.string.cannot_be_empty)
            } else if (chipWoman.isChecked && chipMan.isChecked || !chipWoman.isChecked && !chipMan.isChecked) {
                Snackbar.make(AddContactLayout, getString(R.string.choice_only_one), Snackbar.LENGTH_LONG).show()
                chipMan.error = getString(R.string.choice_only_one)
                chipWoman.error = getString(R.string.choice_only_one)
            } else {

                val contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)
                var choicedGender = ""

                if (chipWoman.isChecked) {
                    choicedGender = getString(R.string.Woman)
                } else if (chipMan.isChecked) {
                    choicedGender = getString(R.string.Man)
                }

                if (editing && id > -1) {
                    contactViewModel.deleteContactWithId(id)
                }

                contactViewModel.insert(
                    Contact(
                        addNameTV.text.toString().trim(),
                        addSurnameTV.text.toString().trim(),
                        addPhoneTV.text.toString().trim().toInt(),
                        addEmailTV.text.toString().trim(),
                        choicedGender.trim()
                    )
                )
                Toast.makeText(this, getString(R.string.contact_added), Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
}
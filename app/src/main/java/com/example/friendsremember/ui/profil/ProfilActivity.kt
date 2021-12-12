package com.example.friendsremember.ui.profil

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.friendsremember.R
import com.example.friendsremember.data.ViewModelFactory
import com.example.friendsremember.data.datastore.UserManager

class ProfilActivity : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "profil")
    val userManager = UserManager.getInstance(dataStore)
    var age = 0
    var name = ""
    var gender = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)

        observeData()
    }
    private fun observeData() {

        val profilViewModel = ViewModelProvider(this, ViewModelFactory(userManager)).get(
            ProfilViewModel::class.java
        )
        val intent = Intent()
        //Updates age
        profilViewModel.getUserAgeFlow().observe(this, {
            age = it
            intent.putExtra(UMUR, age)
            //tv_age.text = it.toString()
        })
        //Updates name
        profilViewModel.getUserNameFlow().observe(this, {
            name = it
            intent.putExtra(NAMA, name)
            //tv_name.text = it.toString()
        })

        //Updates gender
        profilViewModel.getUserGenderFlow().observe(this, {
            gender = if (it) "Male" else "Female"
            intent.putExtra(GENDER, gender)
            //tv_gender.text = gender
        })
    }

    companion object {
        const val UMUR = "umur"
        const val NAMA = "nama"
        const val GENDER = "gender"
    }
}
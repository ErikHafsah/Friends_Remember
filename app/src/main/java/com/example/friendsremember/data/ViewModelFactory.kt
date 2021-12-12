package com.example.friendsremember.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.example.friendsremember.data.datastore.UserManager
import com.example.friendsremember.ui.profil.ProfilViewModel

class ViewModelFactory (private val uManager: UserManager) : NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfilViewModel::class.java)) {
            return ProfilViewModel(uManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}
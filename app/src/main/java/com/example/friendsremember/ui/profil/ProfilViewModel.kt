package com.example.friendsremember.ui.profil

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.friendsremember.data.datastore.UserManager
import kotlinx.coroutines.launch

class ProfilViewModel(private val uManager: UserManager): ViewModel() {
    fun getUserAgeFlow(): LiveData<Int>{
        return uManager.userAgeFlow().asLiveData()
    }
    fun getUserNameFlow(): LiveData<String>{
        return uManager.userNameFlow().asLiveData()
    }
    fun getUserGenderFlow(): LiveData<Boolean>{
        return uManager.userGenderFlow().asLiveData()
    }

    fun saveStoreUser(age: Int, name: String, isMale: Boolean){
        viewModelScope.launch {
            uManager.storeUser(age,name,isMale)
        }
    }
}
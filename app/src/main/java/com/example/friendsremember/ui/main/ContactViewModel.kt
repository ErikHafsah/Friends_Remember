package com.example.friendsremember.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.friendsremember.data.room.Contact
import com.example.friendsremember.data.room.ContactRoomDatabase
import kotlinx.coroutines.launch

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ContactRepository

    val allContacts: LiveData<List<Contact>>

    init {
        val contactDAO = ContactRoomDatabase.getDatabase(application, viewModelScope).contactDAO()
        repository = ContactRepository(contactDAO)
        allContacts = repository.allContacts
    }

    fun insert(contact: Contact) = viewModelScope.launch {
        repository.insert(contact)
    }

    fun deleteContactWithId(id: Int) = viewModelScope.launch {
        repository.deleteContactWithId(id)
    }
}
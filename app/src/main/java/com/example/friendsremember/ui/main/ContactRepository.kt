package com.example.friendsremember.ui.main

import androidx.lifecycle.LiveData
import com.example.friendsremember.data.room.Contact
import com.example.friendsremember.data.room.ContactDAO

class ContactRepository(private val contactDAO: ContactDAO) {

    val allContacts: LiveData<List<Contact>> = contactDAO.getAlphabetizedContacts()

    suspend fun insert(contact: Contact) {
        contactDAO.insert(contact)
    }

    suspend fun deleteContactWithId(id: Int) {
        contactDAO.deleteContactWithId(id)
    }

}
package com.example.camila.lab7.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.camila.lab7.Data.Contact
import com.example.camila.lab7.Data.ContactRepository

class ContactViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: ContactRepository =
        ContactRepository(application)
    private var allContacts: LiveData<List<Contact>> = repository.getAllContacts()

    fun insert(contact: Contact) {
        repository.insert(contact)
    }

    fun update(contact: Contact) {
        repository.update(contact)
    }

    fun delete(contact: Contact) {
        repository.delete(contact)
    }

    fun deleteAllContacts() {
        repository.deleteAllContacts()
    }

    fun getAllContacts(): LiveData<List<Contact>> {
        return allContacts
    }
}
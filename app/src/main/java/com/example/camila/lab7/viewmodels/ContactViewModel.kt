package com.example.camila.lab7.viewmodels
/*      Paula Camila Gonzalez Ortega - Carnet 18398
             Plataformas moviles - Seccion 10
Este modelo se utiliza en las activities para presentar
data en un contacto previamente guardado o nuevo
 */

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.camila.lab7.Data.Contact
import com.example.camila.lab7.Data.ContactRepository

class ContactViewModel(application: Application) : AndroidViewModel(application) {
    // V A R I A B L E S
    private var repository: ContactRepository =
        ContactRepository(application)
    private var allContacts: LiveData<List<Contact>> = repository.getAllContacts()

    // C R U D
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
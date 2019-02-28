package com.example.camila.lab7.Data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactDao{
    @Insert
    fun insert(contact: Contact)

    @Update
    fun update(contact: Contact)

    @Delete
    fun delete(contact: Contact)

    @Query("DELETE FROM contact_table")
    fun deleteAllContacts()

    @Query("SELECT * FROM contact_table ORDER BY priority DESC")
    fun getAllContacts(): LiveData<List<Contact>>
}
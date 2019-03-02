package com.example.camila.lab7.Data
/*      Paula Camila Gonzalez Ortega - Carnet 18398
             Plataformas moviles - Seccion 10
Esta interfaz ofrece las operaciones necesarias para manejar
los contactos
 */
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
package com.example.camila.lab7.Data
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_table")
data class Contact(
    //Los atributos de un contacto
    var name: String,
    var phone: String,
    var mail: String,
    var priority: Int
){
    // M E T O D O S
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
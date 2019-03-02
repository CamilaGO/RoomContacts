package com.example.camila.lab7.Data
/*      Paula Camila Gonzalez Ortega - Carnet 18398
             Plataformas moviles - Seccion 10
Esta clase permite crear objetos de tipo contacto con sus respectivos atributos
para crear una tabla en la DB de ellos
 */
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Blob

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
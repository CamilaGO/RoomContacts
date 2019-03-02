package com.example.camila.lab7
/*      Paula Camila Gonzalez Ortega - Carnet 18398
             Plataformas moviles - Seccion 10
Esta activity permite editar un contacto seleccionado o
crear uno nuevo y guardarlo posteriormente
 */

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_contact.*

class AddEditContactActivity : AppCompatActivity() {

    companion object {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)
        //Valores de prioridad que un contacto puede tener
        number_picker_priority.minValue = 1
        number_picker_priority.maxValue = 10

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        //Se verifica si se editara o se creara un contacto
        if (intent.hasExtra(MainActivity.EXTRA_ID)) {
            title = "Edit Contact"
            //Los campos se rellenan con informacion ya guardada
            edit_text_name.setText(intent.getStringExtra(MainActivity.EXTRA_NAME))
            edit_text_phone.setText(intent.getStringExtra(MainActivity.EXTRA_PHONE))
            edit_text_mail.setText(intent.getStringExtra(MainActivity.EXTRA_MAIL))
            number_picker_priority.value = intent.getIntExtra(MainActivity.EXTRA_PRIORITY, 1)
        } else {
            title = "Add Contact"
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_contact_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        //Si seleccion el icono de la parte superior, los datos ingresados se guardan
        return when (item?.itemId) {
            R.id.save_contact -> {
                saveContact()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun saveContact() {
        if (edit_text_name.text.toString().trim().isBlank() || edit_text_mail.text.toString().trim().isBlank()) {
            //Si no ingreso texto el contacto no se crea
            Toast.makeText(this, "No puedes ingresar un contacto sin nombre!", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent().apply {
            //Si si ingreso informacion, se rellenan las variables
            putExtra(MainActivity.EXTRA_NAME, edit_text_name.text.toString())
            putExtra(MainActivity.EXTRA_PHONE, edit_text_phone.text.toString())
            putExtra(MainActivity.EXTRA_MAIL, edit_text_mail.text.toString())
            putExtra(MainActivity.EXTRA_PRIORITY, number_picker_priority.value)
            if (intent.getIntExtra(MainActivity.EXTRA_ID, -1) != -1) {
                putExtra(MainActivity.EXTRA_ID, intent.getIntExtra(MainActivity.EXTRA_ID, -1))
            }
        }

        setResult(Activity.RESULT_OK, data)
        finish()
    }
}
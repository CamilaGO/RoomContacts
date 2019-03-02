package com.example.camila.lab7
/*      Paula Camila Gonzalez Ortega - Carnet 18398
             Plataformas moviles - Seccion 10
Esta activity muestra los datos de un contacto seleccionado en el main
y permite al usuario enviarle un mail, llamarlo o editar su informacion
 */

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.camila.lab7.Data.Contact
import com.example.camila.lab7.viewmodels.ContactViewModel
import kotlinx.android.synthetic.main.activity_add_contact.*
import kotlinx.android.synthetic.main.activity_ver_contacto.*

class VerContactoActivity : AppCompatActivity() {

    companion object {
        const val EDIT_CONTACT_REQUEST = 3
    }

    lateinit var  contactViewModel: ContactViewModel
    private var currentId:Int= -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_contacto)
        // Se conecta con el ViewModel
        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)
        //Se rellenan los datos
        fillName.text = intent.getStringExtra(MainActivity.EXTRA_NAME)
        fillPhone.text = intent.getStringExtra(MainActivity.EXTRA_PHONE)
        fillMail.text = intent.getStringExtra(MainActivity.EXTRA_MAIL)
        fillPriority.text = intent.getIntExtra(MainActivity.EXTRA_PRIORITY, 1).toString()
        currentId = intent.getIntExtra(MainActivity.EXTRA_ID, 1)

        //Se llama al contacto si hace click es su numero telefonico
        fillPhone.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL,Uri.parse("tel:${intent.getStringExtra(MainActivity.EXTRA_PHONE)}"))
            startActivity(intent)
        }

        //Si apacha en el email, se abrira la actividad para enviar un correo electronico
        fillMail.setOnClickListener{
            val intent  = Intent(this, CorreoActivity::class.java)
            intent.putExtra("recipientMail",MainActivity.EXTRA_MAIL)
            startActivity(intent)
        }

    }

    //Boton para regresar al main
    fun back2Home(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    //Boton para editar la informacion del contacto
    fun editContact(view: View){
        var intent = Intent(baseContext, AddEditContactActivity::class.java)
        //Se obtiene la información del dato previamente almacenada en la DB
        intent.putExtra(MainActivity.EXTRA_ID, currentId)
        intent.putExtra(MainActivity.EXTRA_NAME, fillName.text.toString())
        intent.putExtra(MainActivity.EXTRA_PHONE, fillPhone.text.toString())
        intent.putExtra(MainActivity.EXTRA_MAIL, fillMail.text.toString())
        intent.putExtra(MainActivity.EXTRA_PRIORITY, fillPriority.text.toString())
        startActivityForResult(intent, EDIT_CONTACT_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == EDIT_CONTACT_REQUEST && resultCode == Activity.RESULT_OK) {
            //Si decide editar el contacto
            val id = data?.getIntExtra(MainActivity.EXTRA_ID, -1)

            val updateContact = Contact(
                //Se llenan los campos
                data!!.getStringExtra(MainActivity.EXTRA_NAME),
                data.getStringExtra(MainActivity.EXTRA_PHONE),
                data.getStringExtra(MainActivity.EXTRA_MAIL),
                data.getIntExtra(MainActivity.EXTRA_PRIORITY, 1)
            )

            updateContact.id = id!!
            contactViewModel.update(updateContact)

            //El contacto se actualiza
            fillName.text = updateContact.name
            fillPhone.text = updateContact.phone
            fillMail.text = updateContact.mail
            fillPriority.text = updateContact.priority.toString()

            currentId = updateContact.id
            Toast.makeText(this, "Contacto actualizado!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Contacto no actualizado!", Toast.LENGTH_SHORT).show()
        }
    }


}

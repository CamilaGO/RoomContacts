package com.example.camila.lab7

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)

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


}

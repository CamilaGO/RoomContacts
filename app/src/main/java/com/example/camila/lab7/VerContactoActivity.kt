package com.example.camila.lab7

import android.content.Intent
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
        /*const val EXTRA_ID = "com.example.camila.lab7.EXTRA_ID"
        const val EXTRA_NAME = "com.example.camila.lab7.EXTRA_NAME"
        const val EXTRA_PHONE = "com.example.camila.lab7.EXTRA_PHONE"
        const val EXTRA_MAIL = "com.example.camila.lab7.EXTRA_MAIL"
        const val EXTRA_PRIORITY = "com.example.camila.lab7.EXTRA_PRIORITY"*/
        const val EDIT_CONTACT_REQUEST = 3
    }

    lateinit var  contactViewModel: ContactViewModel
    var currentId:Int= -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_contacto)

        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)

        fillName.text = intent.getStringExtra(MainActivity.EXTRA_NAME)
        fillPhone.text = intent.getStringExtra(MainActivity.EXTRA_PHONE)
        fillMail.text = intent.getStringExtra(MainActivity.EXTRA_MAIL)
        fillPriority.text = intent.getIntExtra(MainActivity.EXTRA_PRIORITY, 1).toString()
        currentId = intent.getIntExtra(MainActivity.EXTRA_ID, 1)
    }

    //Boton para ver activity de nuevo contacto
    fun back2Home(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    /*fun sendMail(view: View) {
        //val intent = Intent(this, CorreoActivity::class.java)
        //startActivity(intent)
    }

    fun makeCall(view: View) {


    }*/
}

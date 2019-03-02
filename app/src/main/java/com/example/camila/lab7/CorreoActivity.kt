package com.example.camila.lab7

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_correo.*

class CorreoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_correo)

        //Si presiona enviar se obtienen datos de los EditText
        sendBtm.setOnClickListener {
            var quien = dest.text.toString().trim()//Obtiene el destinatario
            var asunto = tema.text.toString().trim()//Obtiene el asunto
            var message = mensaje.text.toString().trim()//Obtiene el mensaje
            sendEmail(quien, asunto, message)//Ejecuta la siguiente funcion
        }
    }
    private fun sendEmail(quien: String, asunto: String, message: String){
        val mIntent = Intent(Intent.ACTION_SEND)
        mIntent.data = Uri.parse("mailto:")
        mIntent.type = "text/plain"
        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(quien))
        mIntent.putExtra(Intent.EXTRA_SUBJECT, asunto)
        mIntent.putExtra(Intent.EXTRA_TEXT, message)

        try {
            startActivity(Intent.createChooser(mIntent, "Choose email client..."))

        }
        catch (e: Exception){
            //Si no funciona
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }

    //Boton para ver activity de nuevo contacto
    fun onClick_Back(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}

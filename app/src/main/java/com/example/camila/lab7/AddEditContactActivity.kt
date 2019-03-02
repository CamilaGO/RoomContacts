package com.example.camila.lab7

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

        number_picker_priority.minValue = 1
        number_picker_priority.maxValue = 10

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        if (intent.hasExtra(MainActivity.EXTRA_ID)) {
            title = "Edit Contact"
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
            Toast.makeText(this, "No puedes ingresar un contacto sin nombre!", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent().apply {
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
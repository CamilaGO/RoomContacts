package com.example.camila.lab7
/*      Paula Camila Gonzalez Ortega - Carnet 18398
             Plataformas moviles - Seccion 10
Esta activity presenta los contactos guardados, permite seleccionarlos y ver el "perfil" de un contacto.
Tambien permite agregar un nuevo contacto, borrar uno o borrar todos los contactos
 */

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.camila.lab7.Data.Contact
import com.example.camila.lab7.VerContactoActivity.Companion.EDIT_CONTACT_REQUEST
import com.example.camila.lab7.adapters.ContactAdapter
import com.example.camila.lab7.viewmodels.ContactViewModel
import kotlinx.android.synthetic.main.activity_add_contact.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        // Variables que se manejaran entre los intents y el adaptador de data
        const val EXTRA_ID = "com.example.camila.lab7.EXTRA_ID"
        const val EXTRA_NAME = "com.example.camila.lab7.EXTRA_NAME"
        const val EXTRA_PHONE = "com.example.camila.lab7.EXTRA_PHONE"
        const val EXTRA_MAIL = "com.example.camila.lab7.EXTRA_MAIL"
        const val EXTRA_PRIORITY = "com.example.camila.lab7.EXTRA_PRIORITY"
        const val ADD_CONTACT_REQUEST = 1
        const val SHOW_CONTACT_REQUEST = 2
        lateinit var adapter: ContactAdapter
    }
    private lateinit var contactViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonAddContact.setOnClickListener {
            //Si quiere guardar un nuevo contacto en la DB
            startActivityForResult(
                Intent(this, AddEditContactActivity::class.java),
                ADD_CONTACT_REQUEST
            )
        }
            //Se llena el ReciclerView con los contactos
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        adapter = ContactAdapter()
        recycler_view.adapter = adapter
        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)
        contactViewModel.getAllContacts().observe(this, Observer<List<Contact>> {
            adapter.submitList(it)



        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }
            //Cuando a un contacto se le hace swipe, se borra de la DB
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                contactViewModel.delete(adapter.getContactAt(viewHolder.adapterPosition))
                Toast.makeText(baseContext, "Contacto Eliminado!", Toast.LENGTH_SHORT).show()
            }
        }
        ).attachToRecyclerView(recycler_view)


        adapter.setOnItemClickListener(object : ContactAdapter.OnItemClickListener {
            override fun onItemClick(contact: Contact) {
                //Si presiona uno, se obtiene su informacion y se redirecciona a la VerContactoActivity
                var intent = Intent(baseContext, VerContactoActivity::class.java)
                intent.putExtra(EXTRA_ID, contact.id)
                intent.putExtra(EXTRA_NAME, contact.name)
                intent.putExtra(EXTRA_PHONE, contact.phone)
                intent.putExtra(EXTRA_MAIL, contact.mail)
                intent.putExtra(EXTRA_PRIORITY, contact.priority)

                startActivityForResult(intent, SHOW_CONTACT_REQUEST)
            }
        })


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            //Si desea borrar todos los contactos
            R.id.delete_all_contacts -> {
                contactViewModel.deleteAllContacts()
                Toast.makeText(this, "Eliminaste todos los contactos!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_CONTACT_REQUEST && resultCode == Activity.RESULT_OK) {
            //Si quiere agregar un nuevo contacto a la DB
            val newContact = Contact(
                data!!.getStringExtra(EXTRA_NAME),
                data!!.getStringExtra(EXTRA_PHONE),
                data!!.getStringExtra(EXTRA_MAIL),
                data!!.getIntExtra(EXTRA_PRIORITY, 1)
            )
            contactViewModel.insert(newContact)

            Toast.makeText(this, "Contacto guardado!", Toast.LENGTH_SHORT).show()
        } else if (requestCode == EDIT_CONTACT_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data?.getIntExtra(EXTRA_ID, -1)

            if (id == -1) {
                //Si cometio alguno error y fue imposible actualizar la info
                Toast.makeText(this, "No se pudo actualizar! Error!", Toast.LENGTH_SHORT).show()
            }

            val updateContact = Contact(
                //Si quiere actualizar los datos de un solo user
                data!!.getStringExtra(EXTRA_NAME),
                data.getStringExtra(EXTRA_PHONE),
                data.getStringExtra(EXTRA_MAIL),
                data.getIntExtra(EXTRA_PRIORITY, 1)
            )
            updateContact.id = data.getIntExtra(EXTRA_ID, -1)

        } else {
            //Si cometio alguno error y fue imposible guardar la info
            Toast.makeText(this, "Contacto no guardado!", Toast.LENGTH_SHORT).show()
        }


    }


}

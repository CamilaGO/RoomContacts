package com.example.camila.lab7.Data
/*      Paula Camila Gonzalez Ortega - Carnet 18398
             Plataformas moviles - Seccion 10
Esta clase abstracta implementa la interfaz ContactDao para poder
utilizar los metodos con los contactos
 */

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao


    companion object {
        private var instance: ContactDatabase? = null

        //Se brinda seguridad a la data
        fun getInstance(context: Context): ContactDatabase? {
            if (instance == null) {
                synchronized(ContactDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ContactDatabase::class.java, "contact_database"
                    )
                        .fallbackToDestructiveMigration() // when version increments, it migrates (deletes db and creates new) - else it crashes
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance)
                    .execute()
            }
        }
    }

    class PopulateDbAsyncTask(db: ContactDatabase?) : AsyncTask<Unit, Unit, Unit>() {
        private val contactDao = db?.contactDao()

        override fun doInBackground(vararg p0: Unit?) {
            //Se ingresan datos por default
            contactDao?.insert(Contact("Camila", "55249338", "gon18398@uvg.edu.gt", 1))
            contactDao?.insert(Contact("Eveling", "44972678", "eveling70@gmai.com", 2))
            contactDao?.insert(Contact("Paula", "22335771", "pcgo02@gmail.com", 3))
        }
    }

}
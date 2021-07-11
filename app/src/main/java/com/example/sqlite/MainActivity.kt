package com.example.sqlite

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = DatabaseHandler(this)

//        Inserting contacts
        Log.d("Insert", "Inserting...")
        db.addContact(Contact("Lucky", "0703766814"))
        db.addContact(Contact("Tsuma", "0111687898"))
        db.addContact(Contact("Patience", "0723522865"))
        db.addContact(Contact("Gideon", "0726869071"))
        db.addContact(Contact("Tumu", "0716965478"))

//        Reading all contacts
        Log.d("Reading", "Reading all contacts")
        val contacts = db.getAllContacts()

        for (cn in contacts) {
            val log = "Id: " + cn.getID() + " ,Name: " + cn.getName() + " ,Phone: " +
                    cn.getPhoneNumber()
            // Writing Contacts to log
            Log.d("Name: ", log)
        }
    }
}
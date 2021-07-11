package com.example.sqlite

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf


class DatabaseHandler : SQLiteOpenHelper {
    /*Class extending the SQLiteOpenHelper class must implement the two methods which are onCreate() and onUpgrade() and optionally implement onOpen()*/

    /*Implementing the fields below in a companion object makes them static*/
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "contactsManager"
        private const val TABLE_CONTACTS = "contacts"
        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_PH_NO = "phone_number"
    }

    constructor(context: Context) : super(context, DATABASE_NAME, null, DATABASE_VERSION)
    //3rd argument to be passed is CursorFactory instance


    override fun onCreate(db: SQLiteDatabase?) {
        var CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//        Drop older table if it exists
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
//        Create tables again
        onCreate(db)
    }

    //    Code to add a new contact
    fun addContact(contact: Contact) {

        var db: SQLiteDatabase = this.writableDatabase

//    ContentValues is a map like class that matches a value to a String key.
        val values = contentValuesOf()
        values.put(KEY_NAME, contact.getName())
        values.put(KEY_PH_NO, contact.getPhoneNumber())

//    Inserting a row. The 2nd argument is a string containing the nullColumnHack
        db.insert(TABLE_CONTACTS, null, values)

        db.close()
    }

    //    Code to get the single contact
    fun getContact(id: Int): Contact {
        var db: SQLiteDatabase = this.readableDatabase
        var cursor: Cursor = db.query(
            TABLE_CONTACTS, arrayOf(
                KEY_ID,
                KEY_NAME, KEY_PH_NO
            ),
            "$KEY_ID=?", arrayOf(java.lang.String.valueOf(id)), null, null, null, null
        )
        cursor.moveToFirst()

        val contact =
            Contact(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2))

        return contact
    }

    //    Getting all contacts in a list view
    fun getAllContacts(): List<Contact> {
        val contactList = ArrayList<Contact>()
        val selectQuery = "SELECT * FROM $TABLE_CONTACTS"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

//        looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                val contact = Contact()
                contact.setID(Integer.parseInt(cursor.getString(0)))
                contact.setName(cursor.getString(1))
                contact.setPhoneNumber(cursor.getString(2))

//                Adding contact to list
                contactList.add(contact)
            } while (cursor.moveToNext())
        }

        return contactList
    }

    //   updating the single contact
    fun updateContact(contact: Contact): Int {
        val db = this.writableDatabase

        val values = contentValuesOf()
        values.put(KEY_NAME, contact.getName())
        values.put(KEY_PH_NO, contact.getPhoneNumber())

//        updating row
        return db.update(
            TABLE_CONTACTS,
            values,
            "$KEY_ID=?",
            arrayOf(java.lang.String.valueOf(contact.getID()))
        )
    }

    //    Deleting a single contact
    fun deleteContact(contact: Contact) {
        val db = this.writableDatabase
        db.delete(TABLE_CONTACTS, "$KEY_ID=?", arrayOf(java.lang.String.valueOf(contact.getID())))
        db.close()
    }

    //    Getting contacts Count
    fun getContactsCount(): Int {
        val countQuery = "SELECT * FROM $TABLE_CONTACTS"
        val db = this.readableDatabase
        val cursor = db.rawQuery(countQuery, null)
        cursor.close()

//        return count
        return cursor.count
    }
}
package com.example.sqlite

class Contact {
    private var id: Int? = null
    private var _name: String
    private var _phone_number: String

    constructor(): this(null, "", "")

    constructor(id: Int?, name: String, _phone_number: String) {
        this.id = id
        this._name = name
        this._phone_number = _phone_number
    }

    constructor(name: String, _phone_number: String) {
        this._name = name
        this._phone_number = _phone_number
    }

    fun getID(): Int? {
        return this.id
    }

    fun setID(id: Int) {
        this.id = id
    }

    fun getName(): String {
        return this._name
    }
    fun setName(name: String) {
        this._name = name
    }

    fun getPhoneNumber(): String {
        return this._phone_number
    }

    fun setPhoneNumber(phone_number: String) {
        this._phone_number = phone_number
    }
}
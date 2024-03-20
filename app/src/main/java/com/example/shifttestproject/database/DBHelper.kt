package com.example.shifttestproject.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.shifttestproject.model.DBUser

class DBHelper(val context: Context, val factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "app", factory, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE users (id INT PRIMARY KEY, FIO TEXT, gender TEXT, age TEXT," +
                " nationality TEXT, country TEXT, address TEXT, phoneNumber TEXT, email TEXT, image TEXT)"
        db!!.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }


    fun addUsers(dbUsers: MutableList<DBUser>) {
        clearTable()
        val db = this.writableDatabase
        for (dbUser in dbUsers) {
            val values = ContentValues()
            values.put("FIO", dbUser.FIO)
            values.put("gender", dbUser.gender)
            values.put("age", dbUser.age)
            values.put("nationality", dbUser.nationality)
            values.put("country", dbUser.country)
            values.put("address", dbUser.address)
            values.put("phoneNumber", dbUser.phoneNumber)
            values.put("email", dbUser.email)
            values.put("image", dbUser.image)
            Log.d("MyLog", "image1234: ${dbUser.image}")
            db.insert("users", null, values)
        }
        val res = getUsers().size
        Log.d("MyLog", "size: ${res.toString()}")
        db.close()
    }

    fun clearTable() {
        val db = this.writableDatabase
        db.delete("users", null, null)
        db.close()
    }

    fun getUsers(): MutableList<DBUser> {
        val db = this.readableDatabase
        val bdUsers = mutableListOf<DBUser>()
        val query = "SELECT * FROM users"
        val result = db!!.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val FIO = result.getString(1)
                val gender = result.getString(2)
                val age = result.getString(3)
                val nationality = result.getString(4)
                val country = result.getString(5)
                val address = result.getString(6)
                val phoneNumber = result.getString(7)
                val email = result.getString(8)
                val image = result.getString(9)

                if (FIO == null || gender == null || age == null || nationality == null || country == null
                    || address== null || phoneNumber == null || email== null || image== null) {
                    Log.d("MyLog", "FIO: $FIO")
                    Log.d("MyLog", "age: $age")
                    Log.d("MyLog", "nat: $nationality")
                    Log.d("MyLog", "gender: $gender")
                    Log.d("MyLog", "country: $country")
                    Log.d("MyLog", "address: $address")
                    Log.d("MyLog", "phoneNumber: $phoneNumber")
                    Log.d("MyLog", "image: $image")
                    continue
                }
                val bdUser =
                    DBUser(FIO, gender, age, nationality, country, address, phoneNumber, email, image)
                bdUsers.add(bdUser)
            } while (result.moveToNext())
        }
        return bdUsers
    }
}
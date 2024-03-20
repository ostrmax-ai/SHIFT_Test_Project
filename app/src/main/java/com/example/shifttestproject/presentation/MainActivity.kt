package com.example.shifttestproject.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shifttestproject.R
import com.example.shifttestproject.database.DBHelper
import com.example.shifttestproject.service.ApiService
import com.example.shifttestproject.model.DBUser
import com.example.shifttestproject.model.User
import kotlinx.coroutines.launch
import java.io.IOException

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val NUMBER_OF_USERS = 10

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnUpdate: Button = findViewById(R.id.btn_update)
        val rcvItemList: RecyclerView = findViewById(R.id.rcv_price_list)
        val db = DBHelper(this, null)
        var dbUsers = mutableListOf<DBUser>()
        var users = mutableListOf<User>()

        if (db.getUsers().size != 0) {
            dbUsers = db.getUsers()
            rcvItemList.adapter = ItemAdapter(dbUsers, this@MainActivity)
            rcvItemList.layoutManager = LinearLayoutManager(this@MainActivity)
        } else {
            lifecycleScope.launch {
                users = getNewUsers(db, NUMBER_OF_USERS)
                dbUsers = convertToDBUsers(users)
                rcvItemList.adapter = ItemAdapter(dbUsers, this@MainActivity)
                rcvItemList.layoutManager = LinearLayoutManager(this@MainActivity)
            }
        }

        btnUpdate.setOnClickListener {
            lifecycleScope.launch {
                users = getNewUsers(db, NUMBER_OF_USERS)
                dbUsers = convertToDBUsers(users)
                rcvItemList.adapter = ItemAdapter(dbUsers, this@MainActivity)
                rcvItemList.layoutManager = LinearLayoutManager(this@MainActivity)
                Toast.makeText(this@MainActivity, "Данные обновлены!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private suspend fun getNewUsers(db: DBHelper, number: Int): MutableList<User> {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://randomuser.me")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val users = mutableListOf<User>()
        val service = retrofit.create(ApiService::class.java)
        try {
            val response = service.getUsers(number)

            if (response.isSuccessful && response.body() != null) {
                val userResponse = response.body()
                users.addAll(userResponse!!.results)
                Log.d("MyLog", "users: $users")
                val dbUsers = convertToDBUsers(users)
                db.addUsers(dbUsers)
            } else {
                val errorBody = response.errorBody()?.string()
                Log.d("MyLog", "Error: $errorBody")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }



        return users.toMutableList()
    }

    private fun convertToDBUsers(users: MutableList<User>): MutableList<DBUser> {
        val dbUsers = mutableListOf<DBUser>()
        for (user in users) {
            val name = user.name
            val FIO = name.title + " " + name.first + " " + name.last
            val address = user.location.street.number.toString() + " " + user.location.street.name
            val phoneNumber = user.phone
            val email = user.email
            val gender = user.gender
            val nationality = user.nat
            val country = user.location.country
            val age = user.dob.age.toString()
            val image = user.picture.medium


            val dbUser =
                DBUser(FIO, gender, age, nationality, country, address, phoneNumber, email, image)
            dbUsers.add(dbUser)
        }
        
        return dbUsers
    }
}
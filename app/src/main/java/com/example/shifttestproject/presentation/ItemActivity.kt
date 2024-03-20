package com.example.shifttestproject.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.shifttestproject.R

class ItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)

        val txtFIO: TextView = findViewById(R.id.txt_item_FIO)
        val txtGender: TextView = findViewById(R.id.txt_item_gender)
        val txtNationality: TextView = findViewById(R.id.txt_item_nationality)
        val txtAge: TextView = findViewById(R.id.txt_item_age)
        val txtCountry: TextView = findViewById(R.id.txt_item_country)
        val txtAddress: TextView = findViewById(R.id.txt_item_address)
        val txtPhoneNumber: TextView = findViewById(R.id.txt_item_phone_number)
        val txtEmail: TextView = findViewById(R.id.txt_item_email)

        val FIO = intent.getStringExtra("FIO")
        val address = intent.getStringExtra("address")
        val gender = intent.getStringExtra("gender")
        val country = intent.getStringExtra("country")
        val phoneNumber = intent.getStringExtra("phoneNumber")
        val nationality = intent.getStringExtra("nationality")
        val email = intent.getStringExtra("email")
        val age = intent.getStringExtra("age")

        txtFIO.text = "Возраст: " + FIO
        txtAddress.text = "Адрес: " + address
        txtGender.text = "Гендер" + gender
        txtCountry.text = "Страна: " + country
        txtPhoneNumber.text = "Номер телефона: " + phoneNumber
        txtNationality.text = "Национальность: " + nationality
        txtEmail.text = "Почта: " + email
        txtAge.text = "Возраст: " + age



    }
}
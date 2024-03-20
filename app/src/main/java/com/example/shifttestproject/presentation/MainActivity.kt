package com.example.shifttestproject.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shifttestproject.R
import com.example.shifttestproject.domain.ApiService
import com.example.shifttestproject.domain.User
import kotlinx.coroutines.launch
import java.io.IOException

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val NUMBER_OF_USERS = 10

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rcvItemList: RecyclerView = findViewById(R.id.rcv_price_list)

        lifecycleScope.launch {

            val items = getUsers(NUMBER_OF_USERS)
            rcvItemList.adapter = ItemAdapter(items, this@MainActivity)
            rcvItemList.layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private suspend fun getUsers(number: Int): List<User> {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://randomuser.me")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val users = mutableListOf<User>()

        val service = retrofit.create(ApiService::class.java)

        try {
            val response = service.getUsers(number)
            Log.d("MyLog", "$response")

            if (response.isSuccessful && response.body() != null) {
                val userResponse = response.body()
                users.addAll(userResponse!!.results)
            } else {
                val errorBody = response.errorBody()?.string()
                Log.d("MyLog", "Error: $errorBody")
            }
        } catch (e: IOException) {
            // Обработка ошибки сети или других исключений во время выполнения запроса
            e.printStackTrace()
        }


        return users.toList()
    }

//    fun getResult(string: String): String {
//        var result = ""
//        val url = "https://randomuser.me/api/"
//        val queue = Volley.newRequestQueue(this)
//        val strRequest = StringRequest(
//            Request.Method.GET,
//            url,
//            {
//                    response ->
//                val obj = JSONObject(response)
//                if (string == "FIO") {
//                    val name = obj.getJSONArray("results").getJSONObject(0)
//                        .getJSONObject("name")
//                    result = name.getString("title") + " " + name.getString("first") + " " +
//                            name.getString("last")
//                    Log.d("MyLog", "FIO: $result")
//                }
//                else if (string == "phoneNumber") {
//                    result = obj.getJSONArray("results").getJSONObject(0)
//                        .getString("phone")
//                    Log.d("MyLog", "Phone: $result")
//                }
//                else if (string == "address") {
//                    val street = obj.getJSONArray("results").getJSONObject(0)
//                        .getJSONObject("location").getJSONObject("street")
//                    result += street.getString("number") + " " + street.getString("name")
//                    Log.d("MyLog", "Address: $result")
//                }
//                else if (string == "image") {
//                    result = obj.getJSONArray("results").getJSONObject(0)
//                        .getJSONObject("picture").getString("medium")
//                    Log.d("MyLog", "Image: $result")
//                }
//                else {
//                    Log.d("MyLog", "ERROR: wrong arguments")
//                }
//            },
//            {
//                Log.d("MyLog", "VolleyError: $it")
//            }
//        )
//        queue.add(strRequest)
//        return result
//    }


//    fun getObj() {
//        val url = "https://randomuser.me/api/"
//        val queue = Volley.newRequestQueue(this)
//        var obj = JSONObject()
//        val strRequest = StringRequest(
//            Request.Method.GET,
//            url,
//            {
//                    response->
//                obj = JSONObject(response)
//                Log.d("MyLog", "Obj: ${obj.toString()}")
//            },
//            {
//                Log.d("MyLog", "VolleyError: ${it.toString()}")
//            }
//        )
//        queue.add(strRequest)
//        Log.d("MyLog", "Obj: ${obj.toString()}")
//    }

}
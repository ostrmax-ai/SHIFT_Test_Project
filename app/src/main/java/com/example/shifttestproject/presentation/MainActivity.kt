package com.example.shifttestproject.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.shifttestproject.R
import com.example.shifttestproject.domain.User
import org.json.JSONObject

const val NUMBER_OF_USERS = 20

class MainActivity : AppCompatActivity() {

    private var items = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rcvItemList: RecyclerView = findViewById(R.id.rcv_price_list)
    }

    fun setList() {

        for (i in 1..20) {
            val FIO = getResult("FIO")
            val address = getResult("address")
            val phoneNumber = getResult("phoneNumber")
            val image = getResult("image")

            items.add(User(FIO, address, phoneNumber, image))
        }
    }

    fun getResult(string: String): String {
        var result = ""
        val url = "https://randomuser.me/api/"
        val queue = Volley.newRequestQueue(this)
        val strRequest = StringRequest(
            Request.Method.GET,
            url,
            {
                    response ->
                val obj = JSONObject(response)
                if (string == "FIO") {
                    val name = obj.getJSONArray("results").getJSONObject(0)
                        .getJSONObject("name")
                    result = name.getString("title") + " " + name.getString("first") + " " +
                            name.getString("last")
                    Log.d("MyLog", "FIO: $result")
                }
                else if (string == "phoneNumber") {
                    result = obj.getJSONArray("results").getJSONObject(0)
                        .getString("phone")
                    Log.d("MyLog", "Phone: $result")
                }
                else if (string == "address") {
                    val street = obj.getJSONArray("results").getJSONObject(0)
                        .getJSONObject("location").getJSONObject("street")
                    result += street.getString("number") + " " + street.getString("name")
                    Log.d("MyLog", "Address: $result")
                }
                else if (string == "image") {
                    result = obj.getJSONArray("results").getJSONObject(0)
                        .getJSONObject("picture").getString("medium")
                    Log.d("MyLog", "Image: $result")
                }
                else {
                    Log.d("MyLog", "ERROR: wrong arguments")
                }
            },
            {
                Log.d("MyLog", "VolleyError: $it")
            }
        )
        queue.add(strRequest)
        return result
    }


}
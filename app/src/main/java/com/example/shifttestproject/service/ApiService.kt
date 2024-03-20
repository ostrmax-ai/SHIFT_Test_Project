package com.example.shifttestproject.service
import com.example.shifttestproject.model.UserResponse
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.Query

interface ApiService {
    @GET("/api")
    suspend fun getUsers(@Query("results") results: Int): Response<UserResponse>

}
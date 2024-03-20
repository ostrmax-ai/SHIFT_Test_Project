package com.example.shifttestproject.domain
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.Query

interface ApiService {
    @GET("/api")
    suspend fun getUsers(@Query("results") results: Int): Response<UserResponse>

}
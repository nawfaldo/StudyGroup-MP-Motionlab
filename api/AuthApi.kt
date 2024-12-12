package com.example.pos.api

import com.example.pos.api.dto.auth.LoginRequest
import com.example.pos.api.dto.auth.MeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {
    @POST("/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<Void>

    @GET("/auth/me")
    suspend fun getCurrentUser(@Header("Cookie") cookie: String): Response<MeResponse>
}

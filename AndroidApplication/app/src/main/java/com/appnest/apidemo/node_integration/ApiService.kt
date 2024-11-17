package com.appnest.apidemo.node_integration

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class LoginRequest(val username: String, val password: String)
data class SignupRequest(val username: String, val password: String)
data class LoginResponse(val token: String)
data class SignupResponse(val message: String, val success: Boolean)

interface ApiService {
    @POST("login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("signup")
    fun signup(@Body request: SignupRequest): Call<SignupResponse>
}
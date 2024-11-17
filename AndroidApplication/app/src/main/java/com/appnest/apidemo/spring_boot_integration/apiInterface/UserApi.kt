package com.appnest.apidemo.spring_boot_integration.apiInterface

import com.appnest.apidemo.spring_boot_integration.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {
    @POST("users/create")
    fun createUser(@Body user: User): Call<User>

    @GET("users/all")
    fun getAllUsers(): Call<List<User>>
}

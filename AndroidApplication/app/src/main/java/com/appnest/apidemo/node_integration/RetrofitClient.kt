package com.appnest.apidemo.node_integration

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://<your_computer_ipv4_address>:<Port_Number_Where_Node_Js_Is_Running>/"

//    private const val BASE_URL = "http://10.0.2.2:8080/api/"  // // For emulator to access localhost

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
package com.appnest.apidemo.spring_boot_integration.instance

import com.appnest.apidemo.spring_boot_integration.apiInterface.UserApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object RetrofitInstance {
    private const val BASE_URL = "http://<your_computer_ipv4_address>:<Port_Number_Where_SpringBoot_App_Is_Running>/api/" // For Real Device

//    private const val BASE_URL = "http://10.0.2.2:8080/api/"  // // For emulator to access localhost
    val api: UserApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApi::class.java)
    }
}
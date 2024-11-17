package com.appnest.apidemo.spring_boot_integration.model

data class User(
    val id: Long? = null, // Nullable because it's generated by the backend
    val name: String,
    val email: String,
    val city: String
)


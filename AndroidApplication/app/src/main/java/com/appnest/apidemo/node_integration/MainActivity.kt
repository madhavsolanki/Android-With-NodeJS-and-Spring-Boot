package com.appnest.apidemo.node_integration

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.appnest.apidemo.R
import com.appnest.apidemo.spring_boot_integration.instance.RetrofitInstance
import com.appnest.apidemo.spring_boot_integration.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etCity = findViewById<EditText>(R.id.etCity)
        val btnCreateUser = findViewById<Button>(R.id.btnCreateUser)
        val btnGetAllUsers = findViewById<Button>(R.id.btnGetAllUsers)
        val tvUsers = findViewById<TextView>(R.id.tvUsers)

        // Create User
        btnCreateUser.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val city = etCity.text.toString()

            if (name.isBlank() || email.isBlank() || city.isBlank()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newUser = User(name = name, email = email, city = city)

            RetrofitInstance.api.createUser(newUser).enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful){
                        Toast.makeText(this@MainActivity, "User Created", Toast.LENGTH_SHORT).show()
                        etName.text.clear()
                        etEmail.text.clear()
                        etCity.text.clear()
                    } else {
                        Toast.makeText(this@MainActivity, "Failed to create user", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    Log.d("MainActivity", "onFailure: ${t.message}")
                    println("onFailure: ${t.message}")
                }
            })
        }

        btnGetAllUsers.setOnClickListener {
           RetrofitInstance.api.getAllUsers().enqueue(object : Callback<List<User>> {
               override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                   if (response.isSuccessful) {
                       val users = response.body() ?: emptyList()
                       tvUsers.text = users.joinToString("\n") { "${it.name} (${it.city})" }
                   } else {
                       tvUsers.text = "Failed to load users"
                   }
               }

               override fun onFailure(call: Call<List<User>>, t: Throwable) {
                   tvUsers.text = "Error: ${t.message}"

                   Log.d("MainActivity", "onFailure: ${t.message}")
                   println("onFailure: ${t.message}")

               }

           })
        }

    }
}
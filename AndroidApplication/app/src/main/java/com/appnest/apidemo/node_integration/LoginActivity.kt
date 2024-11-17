package com.appnest.apidemo.node_integration

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.appnest.apidemo.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {


            btnLogin.setOnClickListener {
                val username = etUsername.text.toString().trim()
                val password = etPassword.text.toString().trim()

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this@LoginActivity, "All fields are required", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val apiService = RetrofitClient.instance
                apiService.login(LoginRequest(username, password))
                    .enqueue(object : Callback<LoginResponse> {
                        override fun onResponse(
                            call: Call<LoginResponse>,
                            response: Response<LoginResponse>
                        ) {
                            if (response.isSuccessful) {
                                val token = response.body()?.token
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Login successful! Token: $token",
                                    Toast.LENGTH_LONG
                                ).show()

                            } else {
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Invalid credentials",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            Toast.makeText(
                                this@LoginActivity,
                                "Error: ${t.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
            }

            tvSignup.setOnClickListener {
                startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
            }
        }

    }
}
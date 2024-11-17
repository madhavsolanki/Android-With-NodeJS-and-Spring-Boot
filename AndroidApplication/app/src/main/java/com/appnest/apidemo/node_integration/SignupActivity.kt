package com.appnest.apidemo.node_integration

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.appnest.apidemo.databinding.ActivitySignupBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {


            btnSignup.setOnClickListener {
                val username = etUsername.text.toString().trim()
                val password = etPassword.text.toString().trim()

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this@SignupActivity, "All fields are required", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val apiService = RetrofitClient.instance
                apiService.signup(SignupRequest(username, password)).enqueue(object :
                    Callback<SignupResponse> {
                    override fun onResponse(call: Call<SignupResponse>, response: Response<SignupResponse>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@SignupActivity, "Signup successful!", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this@SignupActivity, "Signup failed", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                        Toast.makeText(this@SignupActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }

        }
    }
}
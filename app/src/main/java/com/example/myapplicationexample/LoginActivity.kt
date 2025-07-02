package com.example.myapplicationexample

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<TextView>(R.id.loginButton)
        val registerNow = findViewById<TextView>(R.id.registerNow)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in both email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            RetrofitClient.instance.login(email, password)
                .enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        val body = response.body()
                        if (response.isSuccessful && body?.status == "success") {
                            Toast.makeText(this@LoginActivity, "Welcome SIGMA ${body.name}", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@LoginActivity, MainPageActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            when (body?.reason) {
                                "wrong_password" -> Toast.makeText(this@LoginActivity, "Incorrect password", Toast.LENGTH_SHORT).show()
                                "no_user" -> Toast.makeText(this@LoginActivity, "User not found", Toast.LENGTH_SHORT).show()
                                "empty_field" -> Toast.makeText(this@LoginActivity, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
                                else -> Toast.makeText(this@LoginActivity, "Login failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
        }

        registerNow.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}

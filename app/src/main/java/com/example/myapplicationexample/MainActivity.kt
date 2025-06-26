package com.example.myapplicationexample

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        RetrofitClient.instance.login("imran@uitm.edu.my", "test123")
            .enqueue(object : retrofit2.Callback<LoginResponse> {
                override fun onResponse(call: retrofit2.Call<LoginResponse>, response: retrofit2.Response<LoginResponse>) {
                    if (response.isSuccessful && response.body()?.status == "success") {
                        val name = response.body()?.name
                          Toast.makeText(this@MainActivity, "Welcome SIGMA $name", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@MainActivity, "Login failed", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: retrofit2.Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })

    }


}
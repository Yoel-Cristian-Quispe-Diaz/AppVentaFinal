package com.example.appventafinal.view


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appventafinal.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val correo = binding.etCorreo.text.toString()
            val contraseña = binding.etContrasea.text.toString()
            // Implement authentication logic here (e.g., API call to verify credentials)
            if (correo.isNotEmpty() && contraseña.isNotEmpty()) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }
}
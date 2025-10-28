package com.example.appventafinal.view


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.appventafinal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                com.example.appventafinal.R.id.nav_usuarios -> replaceFragment(UsuariosFragment())
                com.example.appventafinal.R.id.nav_categorias -> replaceFragment(CategoriasFragment())
                com.example.appventafinal.R.id.nav_productos -> replaceFragment(ProductosFragment())
                com.example.appventafinal.R.id.nav_ventas -> replaceFragment(VentasFragment())
                com.example.appventafinal.R.id.nav_detalle_venta -> replaceFragment(DetalleVentaFragment())
                else -> false
            }
            true
        }

        // Load default fragment
        replaceFragment(UsuariosFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(com.example.appventafinal.R.id.fragment_container, fragment)
            .commit()
    }
}
package com.example.appventafinal.view

import android.os.Bundle
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appventafinal.controller.ProductoViewModel
import com.example.appventafinal.databinding.FragmentProductosBinding
import com.example.appventafinal.databinding.DialogProductoBinding
import com.example.appventafinal.model.Producto

class ProductosFragment : Fragment() {
    private var _binding: FragmentProductosBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProductoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ProductoAdapter()
        binding.rvProductos.layoutManager = LinearLayoutManager(context)
        binding.rvProductos.adapter = adapter

        viewModel.productos.observe(viewLifecycleOwner) { productos ->
            adapter.submitList(productos)
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }

        binding.fabAdd.setOnClickListener { showCreateDialog() }

        viewModel.fetchProductos()
    }

    private fun showCreateDialog() {
        val dialogBinding = DialogProductoBinding.inflate(layoutInflater)
        AlertDialog.Builder(requireContext())
            .setTitle("Nuevo Producto")
            .setView(dialogBinding.root)
            .setPositiveButton("Guardar") { _, _ ->
                val nombre = dialogBinding.etNombre.text.toString().trim()
                val descripcion = dialogBinding.etDescripcion.text.toString().trim().ifEmpty { null }
                val precio = dialogBinding.etPrecio.text.toString().toDoubleOrNull()
                val stock = dialogBinding.etStock.text.toString().toIntOrNull()
                val idCategoria = dialogBinding.etIdCategoria.text.toString().toIntOrNull()

                if (nombre.isNotEmpty() && precio != null && stock != null) {
                    val productoCreate = com.example.appventafinal.model.ProductoCreate(
                        nombre, descripcion, precio, stock, idCategoria, null
                    )
                    viewModel.createProducto(productoCreate)
                } else {
                    Toast.makeText(context, "Completa nombre, precio y stock", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .create()
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
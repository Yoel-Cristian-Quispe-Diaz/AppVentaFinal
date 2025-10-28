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
import com.example.appventafinal.controller.CategoriaViewModel
import com.example.appventafinal.databinding.FragmentProductosBinding
import com.example.appventafinal.databinding.DialogProductoBinding
import com.example.appventafinal.model.Producto
import com.example.appventafinal.view.ProductoAdapter
import android.widget.ArrayAdapter

class ProductosFragment : Fragment() {
    private var _binding: FragmentProductosBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProductoViewModel by viewModels()
    private val categoriaViewModel: CategoriaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ProductoAdapter { producto -> showEditDialog(producto) }
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
        categoriaViewModel.fetchCategorias()
    }

    private fun showCreateDialog() {
        val dialogBinding = DialogProductoBinding.inflate(layoutInflater)
        // Populate category dropdown
        categoriaViewModel.categorias.value?.let { categorias ->
            val names = categorias.map { it.nombre }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, names)
            dialogBinding.etIdCategoria.setAdapter(adapter)
        }
        categoriaViewModel.categorias.observe(viewLifecycleOwner) { categorias ->
            val names = categorias.map { it.nombre }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, names)
            dialogBinding.etIdCategoria.setAdapter(adapter)
        }
        AlertDialog.Builder(requireContext())
            .setTitle("Nuevo Producto")
            .setView(dialogBinding.root)
            .setPositiveButton("Guardar") { _, _ ->
                val nombre = dialogBinding.etNombre.text.toString().trim()
                val descripcion = dialogBinding.etDescripcion.text.toString().trim().ifEmpty { null }
                val precio = dialogBinding.etPrecio.text.toString().toDoubleOrNull()
                val stock = dialogBinding.etStock.text.toString().toIntOrNull()
                val selectedCategoriaName = dialogBinding.etIdCategoria.text.toString().trim()
                val idCategoria = categoriaViewModel.categorias.value
                    ?.firstOrNull { it.nombre == selectedCategoriaName }
                    ?.idCategoria

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

    private fun showEditDialog(producto: Producto) {
        val dialogBinding = DialogProductoBinding.inflate(layoutInflater)
        dialogBinding.etNombre.setText(producto.nombre)
        dialogBinding.etDescripcion.setText(producto.descripcion ?: "")
        dialogBinding.etPrecio.setText(producto.precio.toString())
        dialogBinding.etStock.setText(producto.stock.toString())

        // Populate category dropdown and preselect
        categoriaViewModel.categorias.value?.let { categorias ->
            val names = categorias.map { it.nombre }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, names)
            dialogBinding.etIdCategoria.setAdapter(adapter)
            val selectedName = categorias.firstOrNull { it.idCategoria == producto.idCategoria }?.nombre
            if (selectedName != null) dialogBinding.etIdCategoria.setText(selectedName, false)
        }
        categoriaViewModel.categorias.observe(viewLifecycleOwner) { categorias ->
            val names = categorias.map { it.nombre }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, names)
            dialogBinding.etIdCategoria.setAdapter(adapter)
            val selectedName = categorias.firstOrNull { it.idCategoria == producto.idCategoria }?.nombre
            if (selectedName != null) dialogBinding.etIdCategoria.setText(selectedName, false)
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Editar Producto")
            .setView(dialogBinding.root)
            .setPositiveButton("Guardar") { _, _ ->
                val nombre = dialogBinding.etNombre.text.toString().trim()
                val descripcion = dialogBinding.etDescripcion.text.toString().trim().ifEmpty { null }
                val precio = dialogBinding.etPrecio.text.toString().toDoubleOrNull()
                val stock = dialogBinding.etStock.text.toString().toIntOrNull()
                val selectedCategoriaName = dialogBinding.etIdCategoria.text.toString().trim()
                val idCategoria = categoriaViewModel.categorias.value
                    ?.firstOrNull { it.nombre == selectedCategoriaName }
                    ?.idCategoria

                if (nombre.isNotEmpty() && precio != null && stock != null) {
                    val productoCreate = com.example.appventafinal.model.ProductoCreate(
                        nombre, descripcion, precio, stock, idCategoria, null
                    )
                    viewModel.updateProducto(producto.idProducto, productoCreate)
                } else {
                    Toast.makeText(context, "Completa nombre, precio y stock", Toast.LENGTH_SHORT).show()
                }
            }
            .setNeutralButton("Eliminar") { _, _ ->
                viewModel.deleteProducto(producto.idProducto)
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
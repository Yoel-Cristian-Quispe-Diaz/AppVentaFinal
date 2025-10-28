package com.example.appventafinal.view


import android.os.Bundle
import android.app.AlertDialog
import android.widget.Toast
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appventafinal.controller.CategoriaViewModel
import com.example.appventafinal.databinding.FragmentCategoriasBinding
import com.example.appventafinal.databinding.DialogCategoriaBinding
import com.example.appventafinal.model.Categoria

class CategoriasFragment : Fragment() {
    private var _binding: FragmentCategoriasBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CategoriaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CategoriaAdapter { categoria -> showEditDialog(categoria) }
        binding.rvCategorias.layoutManager = LinearLayoutManager(context)
        binding.rvCategorias.adapter = adapter

        viewModel.categorias.observe(viewLifecycleOwner) { categorias ->
            adapter.submitList(categorias)
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }

        binding.fabAdd.setOnClickListener { showCreateDialog() }

        viewModel.fetchCategorias()
    }

    private fun showCreateDialog() {
        val dialogBinding = DialogCategoriaBinding.inflate(layoutInflater)
        AlertDialog.Builder(requireContext())
            .setTitle("Nueva Categoría")
            .setView(dialogBinding.root)
            .setPositiveButton("Guardar") { _, _ ->
                val nombre = dialogBinding.etNombre.text.toString().trim()
                val descripcion = dialogBinding.etDescripcion.text.toString().trim().ifEmpty { null }

                if (nombre.isNotEmpty()) {
                    val categoriaCreate = com.example.appventafinal.model.CategoriaCreate(
                        nombre, descripcion
                    )
                    viewModel.createCategoria(categoriaCreate)
                } else {
                    Toast.makeText(context, "Completa el nombre", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .create()
            .show()
    }

    private fun showEditDialog(categoria: Categoria) {
        val dialogBinding = DialogCategoriaBinding.inflate(layoutInflater)
        dialogBinding.etNombre.setText(categoria.nombre)
        dialogBinding.etDescripcion.setText(categoria.descripcion ?: "")

        AlertDialog.Builder(requireContext())
            .setTitle("Editar Categoría")
            .setView(dialogBinding.root)
            .setPositiveButton("Guardar") { _, _ ->
                val nombre = dialogBinding.etNombre.text.toString().trim()
                val descripcion = dialogBinding.etDescripcion.text.toString().trim().ifEmpty { null }

                if (nombre.isNotEmpty()) {
                    val categoriaCreate = com.example.appventafinal.model.CategoriaCreate(
                        nombre, descripcion
                    )
                    viewModel.updateCategoria(categoria.idCategoria, categoriaCreate)
                } else {
                    Toast.makeText(context, "Completa el nombre", Toast.LENGTH_SHORT).show()
                }
            }
            .setNeutralButton("Eliminar") { _, _ ->
                viewModel.deleteCategoria(categoria.idCategoria)
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
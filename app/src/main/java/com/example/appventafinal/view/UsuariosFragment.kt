package com.example.appventafinal.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.app.AlertDialog
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appventafinal.controller.UsuarioViewModel
import com.example.appventafinal.databinding.FragmentUsuariosBinding
import com.example.appventafinal.databinding.DialogUsuarioBinding
import com.example.appventafinal.model.Usuario

class UsuariosFragment : Fragment() {
    private var _binding: FragmentUsuariosBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UsuarioViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsuariosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = UsuarioAdapter { usuario -> showEditDialog(usuario) }
        binding.rvUsuarios.layoutManager = LinearLayoutManager(context)
        binding.rvUsuarios.adapter = adapter

        viewModel.usuarios.observe(viewLifecycleOwner) { usuarios ->
            adapter.submitList(usuarios)
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }

        binding.fabAdd.setOnClickListener { showCreateDialog() }

        viewModel.fetchUsuarios()
    }

    private fun showEditDialog(usuario: com.example.appventafinal.model.Usuario) {
        val dialogBinding = com.example.appventafinal.databinding.DialogUsuarioBinding.inflate(layoutInflater)
        dialogBinding.etNombre.setText(usuario.nombre)
        dialogBinding.etCorreo.setText(usuario.correo)
        dialogBinding.etContrasena.setText(usuario.contraseña)
        dialogBinding.etTelefono.setText(usuario.telefono ?: "")
        dialogBinding.etDireccion.setText(usuario.direccion ?: "")
        dialogBinding.etRol.setText(usuario.rol)

        AlertDialog.Builder(requireContext())
            .setTitle("Editar Usuario")
            .setView(dialogBinding.root)
            .setPositiveButton("Guardar") { _, _ ->
                val nombre = dialogBinding.etNombre.text.toString().trim()
                val correo = dialogBinding.etCorreo.text.toString().trim()
                val contrasena = dialogBinding.etContrasena.text.toString().trim()
                val telefono = dialogBinding.etTelefono.text.toString().trim().ifEmpty { null }
                val direccion = dialogBinding.etDireccion.text.toString().trim().ifEmpty { null }
                val rol = dialogBinding.etRol.text.toString().trim().ifEmpty { "cliente" }

                if (nombre.isNotEmpty() && correo.isNotEmpty() && contrasena.isNotEmpty()) {
                    val usuarioCreate = com.example.appventafinal.model.UsuarioCreate(
                        nombre, correo, contrasena, telefono, direccion, rol
                    )
                    viewModel.updateUsuario(usuario.idUsuario, usuarioCreate)
                } else {
                    Toast.makeText(context, "Completa nombre, correo y contraseña", Toast.LENGTH_SHORT).show()
                }
            }
            .setNeutralButton("Eliminar") { _, _ ->
                viewModel.deleteUsuario(usuario.idUsuario)
            }
            .setNegativeButton("Cancelar", null)
            .create()
            .show()
    }

    private fun showCreateDialog() {
        val dialogBinding = DialogUsuarioBinding.inflate(layoutInflater)
        AlertDialog.Builder(requireContext())
            .setTitle("Nuevo Usuario")
            .setView(dialogBinding.root)
            .setPositiveButton("Guardar") { _, _ ->
                val nombre = dialogBinding.etNombre.text.toString().trim()
                val correo = dialogBinding.etCorreo.text.toString().trim()
                val contrasena = dialogBinding.etContrasena.text.toString().trim()
                val telefono = dialogBinding.etTelefono.text.toString().trim().ifEmpty { null }
                val direccion = dialogBinding.etDireccion.text.toString().trim().ifEmpty { null }
                val rol = dialogBinding.etRol.text.toString().trim().ifEmpty { "cliente" }

                if (nombre.isNotEmpty() && correo.isNotEmpty() && contrasena.isNotEmpty()) {
                    val usuarioCreate = com.example.appventafinal.model.UsuarioCreate(
                        nombre, correo, contrasena, telefono, direccion, rol
                    )
                    viewModel.createUsuario(usuarioCreate)
                } else {
                    Toast.makeText(context, "Completa nombre, correo y contraseña", Toast.LENGTH_SHORT).show()
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
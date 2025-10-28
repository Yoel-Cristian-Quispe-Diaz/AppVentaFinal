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
import com.example.appventafinal.controller.VentaViewModel
import com.example.appventafinal.controller.UsuarioViewModel
import com.example.appventafinal.databinding.FragmentVentasBinding
import com.example.appventafinal.databinding.DialogVentaBinding
import com.example.appventafinal.model.Venta
import android.widget.ArrayAdapter

class VentasFragment : Fragment() {
    private var _binding: FragmentVentasBinding? = null
    private val binding get() = _binding!!
    private val viewModel: VentaViewModel by viewModels()
    private val usuarioViewModel: UsuarioViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVentasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = VentaAdapter { venta -> showEditDialog(venta) }
        binding.rvVentas.layoutManager = LinearLayoutManager(context)
        binding.rvVentas.adapter = adapter

        viewModel.ventas.observe(viewLifecycleOwner) { ventas ->
            adapter.submitList(ventas)
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }

        binding.fabAdd.setOnClickListener { showCreateDialog() }

        viewModel.fetchVentas()
        usuarioViewModel.fetchUsuarios()
    }

    private fun showCreateDialog() {
        val dialogBinding = DialogVentaBinding.inflate(layoutInflater)
        // Populate users dropdown
        usuarioViewModel.usuarios.value?.let { usuarios ->
            val names = usuarios.map { it.nombre }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, names)
            dialogBinding.etIdUsuario.setAdapter(adapter)
        }
        usuarioViewModel.usuarios.observe(viewLifecycleOwner) { usuarios ->
            val names = usuarios.map { it.nombre }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, names)
            dialogBinding.etIdUsuario.setAdapter(adapter)
        }
        // Método de pago dropdown
        val metodos = listOf("Efectivo", "Tarjeta", "Transferencia")
        dialogBinding.etMetodoPago.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, metodos))
        AlertDialog.Builder(requireContext())
            .setTitle("Nueva Venta")
            .setView(dialogBinding.root)
            .setPositiveButton("Guardar") { _, _ ->
                val selectedUsuarioName = dialogBinding.etIdUsuario.text.toString().trim()
                val idUsuario = usuarioViewModel.usuarios.value
                    ?.firstOrNull { it.nombre == selectedUsuarioName }
                    ?.idUsuario
                val total = dialogBinding.etTotal.text.toString().toDoubleOrNull()
                val metodoPago = dialogBinding.etMetodoPago.text.toString().trim()

                if (total != null && metodoPago.isNotEmpty()) {
                    val ventaCreate = com.example.appventafinal.model.VentaCreate(
                        idUsuario, total, metodoPago
                    )
                    viewModel.createVenta(ventaCreate)
                } else {
                    Toast.makeText(context, "Completa total y método de pago", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .create()
            .show()
    }

    private fun showEditDialog(venta: Venta) {
        val dialogBinding = DialogVentaBinding.inflate(layoutInflater)
        // Populate users dropdown and preselect
        usuarioViewModel.usuarios.value?.let { usuarios ->
            val names = usuarios.map { it.nombre }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, names)
            dialogBinding.etIdUsuario.setAdapter(adapter)
            val selectedName = usuarios.firstOrNull { it.idUsuario == venta.idUsuario }?.nombre
            if (selectedName != null) dialogBinding.etIdUsuario.setText(selectedName, false)
        }
        usuarioViewModel.usuarios.observe(viewLifecycleOwner) { usuarios ->
            val names = usuarios.map { it.nombre }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, names)
            dialogBinding.etIdUsuario.setAdapter(adapter)
            val selectedName = usuarios.firstOrNull { it.idUsuario == venta.idUsuario }?.nombre
            if (selectedName != null) dialogBinding.etIdUsuario.setText(selectedName, false)
        }
        // Método de pago dropdown and preselect
        val metodos = listOf("Efectivo", "Tarjeta", "Transferencia")
        dialogBinding.etMetodoPago.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, metodos))
        dialogBinding.etMetodoPago.setText(venta.metodoPago, false)
        dialogBinding.etTotal.setText(venta.total.toString())

        AlertDialog.Builder(requireContext())
            .setTitle("Editar Venta")
            .setView(dialogBinding.root)
            .setPositiveButton("Guardar") { _, _ ->
                val selectedUsuarioName = dialogBinding.etIdUsuario.text.toString().trim()
                val idUsuario = usuarioViewModel.usuarios.value
                    ?.firstOrNull { it.nombre == selectedUsuarioName }
                    ?.idUsuario
                val total = dialogBinding.etTotal.text.toString().toDoubleOrNull()
                val metodoPago = dialogBinding.etMetodoPago.text.toString().trim()

                if (total != null && metodoPago.isNotEmpty()) {
                    val ventaCreate = com.example.appventafinal.model.VentaCreate(
                        idUsuario, total, metodoPago
                    )
                    viewModel.updateVenta(venta.idVenta, ventaCreate)
                } else {
                    Toast.makeText(context, "Completa total y método de pago", Toast.LENGTH_SHORT).show()
                }
            }
            .setNeutralButton("Eliminar") { _, _ ->
                viewModel.deleteVenta(venta.idVenta)
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
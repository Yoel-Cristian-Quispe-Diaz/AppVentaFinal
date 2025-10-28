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
import com.example.appventafinal.databinding.FragmentVentasBinding
import com.example.appventafinal.databinding.DialogVentaBinding
import com.example.appventafinal.model.Venta

class VentasFragment : Fragment() {
    private var _binding: FragmentVentasBinding? = null
    private val binding get() = _binding!!
    private val viewModel: VentaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVentasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = VentaAdapter()
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
    }

    private fun showCreateDialog() {
        val dialogBinding = DialogVentaBinding.inflate(layoutInflater)
        AlertDialog.Builder(requireContext())
            .setTitle("Nueva Venta")
            .setView(dialogBinding.root)
            .setPositiveButton("Guardar") { _, _ ->
                val idUsuario = dialogBinding.etIdUsuario.text.toString().toIntOrNull()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
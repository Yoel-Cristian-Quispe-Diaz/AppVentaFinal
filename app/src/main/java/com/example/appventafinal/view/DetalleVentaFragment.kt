package com.example.appventafinal.view


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appventafinal.controller.DetalleVentaViewModel
import com.example.appventafinal.databinding.DialogDetalleVentaBinding
import com.example.appventafinal.databinding.FragmentDetalleVentaBinding
import com.example.appventafinal.model.DetalleVenta
import com.example.appventafinal.model.DetalleVentaCreate

class DetalleVentaFragment : Fragment() {
    private var _binding: FragmentDetalleVentaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetalleVentaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleVentaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = DetalleVentaAdapter { detalle -> showEditDialog(detalle) }
        binding.rvDetallesVenta.layoutManager = LinearLayoutManager(context)
        binding.rvDetallesVenta.adapter = adapter

        viewModel.detallesVenta.observe(viewLifecycleOwner) { detalles ->
            adapter.submitList(detalles)
        }

        viewModel.productos.observe(viewLifecycleOwner) { productos ->
            adapter.setProductos(productos)
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }

        binding.fabAdd.setOnClickListener { showCreateDialog() }

        viewModel.fetchDetallesVenta()
        viewModel.fetchProductos()
    }

    private fun showCreateDialog() {
        val dialogBinding = DialogDetalleVentaBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Nuevo Detalle de Venta")
            .setView(dialogBinding.root)
            .setPositiveButton("Guardar") { _, _ ->
                val idVenta = dialogBinding.etIdVenta.text.toString().toIntOrNull()
                val selectedPosition = dialogBinding.spProducto.selectedItemPosition
                val selectedProducto = viewModel.productos.value?.getOrNull(selectedPosition)
                val idProducto = selectedProducto?.idProducto
                val cantidad = dialogBinding.etCantidad.text.toString().toIntOrNull()
                val precioUnitario = dialogBinding.etPrecioUnitario.text.toString().toDoubleOrNull()

                if (idVenta != null && idProducto != null && cantidad != null && precioUnitario != null) {
                    val detalle = DetalleVentaCreate(idVenta, idProducto, cantidad, precioUnitario)
                    viewModel.createDetalleVenta(detalle)
                } else {
                    Toast.makeText(context, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .create()

        // Configurar spinner de productos
        viewModel.productos.observe(viewLifecycleOwner) { productos ->
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                productos.map { it.nombre }
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            dialogBinding.spProducto.adapter = adapter
        }

        dialog.show()
    }

    private fun showEditDialog(detalle: DetalleVenta) {
        val dialogBinding = DialogDetalleVentaBinding.inflate(layoutInflater)
        dialogBinding.etIdVenta.setText(detalle.idVenta.toString())
        dialogBinding.etCantidad.setText(detalle.cantidad.toString())
        dialogBinding.etPrecioUnitario.setText(detalle.precioUnitario.toString())

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Editar Detalle de Venta")
            .setView(dialogBinding.root)
            .setPositiveButton("Guardar") { _, _ ->
                val idVenta = dialogBinding.etIdVenta.text.toString().toIntOrNull()
                val selectedPosition = dialogBinding.spProducto.selectedItemPosition
                val selectedProducto = viewModel.productos.value?.getOrNull(selectedPosition)
                val idProducto = selectedProducto?.idProducto
                val cantidad = dialogBinding.etCantidad.text.toString().toIntOrNull()
                val precioUnitario = dialogBinding.etPrecioUnitario.text.toString().toDoubleOrNull()

                if (idVenta != null && idProducto != null && cantidad != null && precioUnitario != null) {
                    val detalleCreate = DetalleVentaCreate(idVenta, idProducto, cantidad, precioUnitario)
                    viewModel.updateDetalleVenta(detalle.idDetalle, detalleCreate)
                } else {
                    Toast.makeText(context, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .setNeutralButton("Eliminar") { _, _ ->
                viewModel.deleteDetalleVenta(detalle.idDetalle)
            }
            .create()

        // Configurar spinner de productos
        viewModel.productos.observe(viewLifecycleOwner) { productos ->
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                productos.map { it.nombre }
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            dialogBinding.spProducto.adapter = adapter
            dialogBinding.spProducto.setSelection(detalle.idProducto - 1) // Ajustar posición
        }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
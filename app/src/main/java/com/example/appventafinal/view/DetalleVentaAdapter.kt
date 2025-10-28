package com.example.appventafinal.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appventafinal.databinding.ItemDetalleVentaBinding
import com.example.appventafinal.model.DetalleVenta
import com.example.appventafinal.model.Producto

class DetalleVentaAdapter(
    private val onItemClick: (DetalleVenta) -> Unit
) : ListAdapter<DetalleVenta, DetalleVentaAdapter.DetalleVentaViewHolder>(DetalleVentaDiffCallback()) {
    private var productos: List<Producto> = emptyList()

    fun setProductos(productos: List<Producto>) {
        this.productos = productos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetalleVentaViewHolder {
        val binding = ItemDetalleVentaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetalleVentaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetalleVentaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class DetalleVentaViewHolder(private val binding: ItemDetalleVentaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(detalle: DetalleVenta) {
            val producto = productos.find { it.idProducto == detalle.idProducto }
            binding.tvIdVenta.text = "Venta #${detalle.idVenta}"
            binding.tvProductoNombre.text = producto?.nombre ?: "Producto #${detalle.idProducto}"
            binding.tvCantidad.text = "Cantidad: ${detalle.cantidad}"
            binding.tvSubtotal.text = "Subtotal: $${detalle.subtotal}"
            binding.root.setOnClickListener { onItemClick(detalle) }
        }
    }
}

class DetalleVentaDiffCallback : DiffUtil.ItemCallback<DetalleVenta>() {
    override fun areItemsTheSame(oldItem: DetalleVenta, newItem: DetalleVenta): Boolean {
        return oldItem.idDetalle == newItem.idDetalle
    }

    override fun areContentsTheSame(oldItem: DetalleVenta, newItem: DetalleVenta): Boolean {
        return oldItem == newItem
    }
}
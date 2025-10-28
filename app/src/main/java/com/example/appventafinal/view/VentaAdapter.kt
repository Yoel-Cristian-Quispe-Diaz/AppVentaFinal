package com.example.appventafinal.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appventafinal.databinding.ItemVentaBinding
import com.example.appventafinal.model.Venta

class VentaAdapter(
    private val onItemClick: (Venta) -> Unit
) : ListAdapter<Venta, VentaAdapter.VentaViewHolder>(VentaDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VentaViewHolder {
        val binding = ItemVentaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VentaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VentaViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }

    class VentaViewHolder(private val binding: ItemVentaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(venta: Venta, onItemClick: (Venta) -> Unit) {
            binding.tvIdVenta.text = "Venta #${venta.idVenta}"
            binding.tvTotal.text = "$${venta.total}"
            binding.tvMetodoPago.text = venta.metodoPago
            binding.tvFecha.text = venta.fecha
            binding.root.setOnClickListener { onItemClick(venta) }
        }
    }
}

class VentaDiffCallback : DiffUtil.ItemCallback<Venta>() {
    override fun areItemsTheSame(oldItem: Venta, newItem: Venta): Boolean {
        return oldItem.idVenta == newItem.idVenta
    }

    override fun areContentsTheSame(oldItem: Venta, newItem: Venta): Boolean {
        return oldItem == newItem
    }
}
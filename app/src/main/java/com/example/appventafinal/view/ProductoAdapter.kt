package com.example.appventafinal.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appventafinal.databinding.ItemProductoBinding
import com.example.appventafinal.model.Producto

class ProductoAdapter(
    private val onItemClick: (Producto) -> Unit
) : ListAdapter<Producto, ProductoAdapter.ProductoViewHolder>(ProductoDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val binding = ItemProductoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }

    class ProductoViewHolder(private val binding: ItemProductoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(producto: Producto, onItemClick: (Producto) -> Unit) {
            binding.tvNombre.text = producto.nombre
            binding.tvPrecio.text = "$${producto.precio}"
            binding.tvStock.text = "Stock: ${producto.stock}"
            binding.root.setOnClickListener { onItemClick(producto) }
        }
    }
}

class ProductoDiffCallback : DiffUtil.ItemCallback<Producto>() {
    override fun areItemsTheSame(oldItem: Producto, newItem: Producto): Boolean {
        return oldItem.idProducto == newItem.idProducto
    }

    override fun areContentsTheSame(oldItem: Producto, newItem: Producto): Boolean {
        return oldItem == newItem
    }
}
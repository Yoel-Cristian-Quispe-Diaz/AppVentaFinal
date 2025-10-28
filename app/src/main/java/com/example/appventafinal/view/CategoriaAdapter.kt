package com.example.appventafinal.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appventafinal.databinding.ItemCategoriaBinding
import com.example.appventafinal.model.Categoria

class CategoriaAdapter(
    private val onItemClick: (Categoria) -> Unit
) : ListAdapter<Categoria, CategoriaAdapter.CategoriaViewHolder>(CategoriaDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val binding = ItemCategoriaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoriaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }

    class CategoriaViewHolder(private val binding: ItemCategoriaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(categoria: Categoria, onItemClick: (Categoria) -> Unit) {
            binding.tvNombre.text = categoria.nombre
            binding.tvDescripcion.text = categoria.descripcion ?: "Sin descripción"
            binding.root.setOnClickListener { onItemClick(categoria) }
        }
    }
}

class CategoriaDiffCallback : DiffUtil.ItemCallback<Categoria>() {
    override fun areItemsTheSame(oldItem: Categoria, newItem: Categoria): Boolean {
        return oldItem.idCategoria == newItem.idCategoria
    }

    override fun areContentsTheSame(oldItem: Categoria, newItem: Categoria): Boolean {
        return oldItem == newItem
    }
}
package com.example.appventafinal.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appventafinal.databinding.ItemUsuarioBinding
import com.example.appventafinal.model.Usuario

class UsuarioAdapter : ListAdapter<Usuario, UsuarioAdapter.UsuarioViewHolder>(UsuarioDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val binding = ItemUsuarioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsuarioViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class UsuarioViewHolder(private val binding: ItemUsuarioBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(usuario: Usuario) {
            binding.tvNombre.text = usuario.nombre
            binding.tvCorreo.text = usuario.correo
            binding.tvRol.text = usuario.rol
        }
    }
}

class UsuarioDiffCallback : DiffUtil.ItemCallback<Usuario>() {
    override fun areItemsTheSame(oldItem: Usuario, newItem: Usuario): Boolean {
        return oldItem.idUsuario == newItem.idUsuario
    }

    override fun areContentsTheSame(oldItem: Usuario, newItem: Usuario): Boolean {
        return oldItem == newItem
    }
}
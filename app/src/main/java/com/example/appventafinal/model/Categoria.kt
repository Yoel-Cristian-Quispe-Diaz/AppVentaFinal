package com.example.appventafinal.model


import com.google.gson.annotations.SerializedName

data class Categoria(
    @SerializedName("id_categoria") val idCategoria: Int,
    val nombre: String,
    val descripcion: String?
)

data class CategoriaCreate(
    val nombre: String,
    val descripcion: String? = null
)
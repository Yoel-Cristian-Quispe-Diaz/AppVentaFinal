package com.example.appventafinal.model


import com.google.gson.annotations.SerializedName

data class Producto(
    @SerializedName("id_producto") val idProducto: Int,
    val nombre: String,
    val descripcion: String?,
    val precio: Double,
    val stock: Int,
    @SerializedName("id_categoria") val idCategoria: Int?,
    val imagen: String?
)

data class ProductoCreate(
    val nombre: String,
    val descripcion: String? = null,
    val precio: Double,
    val stock: Int,
    @SerializedName("id_categoria") val idCategoria: Int? = null,
    val imagen: String? = null
)
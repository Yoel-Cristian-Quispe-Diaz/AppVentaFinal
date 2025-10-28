package com.example.appventafinal.model

import com.google.gson.annotations.SerializedName

data class Usuario(
    @SerializedName("id_usuario") val idUsuario: Int,
    val nombre: String,
    val correo: String,
    val contraseña: String,
    val telefono: String?,
    val direccion: String?,
    val rol: String
)

data class UsuarioCreate(
    val nombre: String,
    val correo: String,
    val contraseña: String,
    val telefono: String? = null,
    val direccion: String? = null,
    val rol: String = "cliente"
)
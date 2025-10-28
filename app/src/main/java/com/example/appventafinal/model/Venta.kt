package com.example.appventafinal.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Venta(
    @SerializedName("id_venta") val idVenta: Int,
    @SerializedName("id_usuario") val idUsuario: Int?,
    val fecha: String,
    val total: Double,
    @SerializedName("metodo_pago") val metodoPago: String
)

data class VentaCreate(
    @SerializedName("id_usuario") val idUsuario: Int? = null,
    val total: Double,
    @SerializedName("metodo_pago") val metodoPago: String
)
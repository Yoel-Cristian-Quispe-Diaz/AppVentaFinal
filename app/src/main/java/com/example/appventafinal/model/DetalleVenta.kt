package com.example.appventafinal.model

import com.google.gson.annotations.SerializedName

data class DetalleVenta(
    @SerializedName("id_detalle") val idDetalle: Int,
    @SerializedName("id_venta") val idVenta: Int,
    @SerializedName("id_producto") val idProducto: Int,
    val cantidad: Int,
    @SerializedName("precio_unitario") val precioUnitario: Double,
    val subtotal: Double
)

data class DetalleVentaCreate(
    @SerializedName("id_venta") val idVenta: Int,
    @SerializedName("id_producto") val idProducto: Int,
    val cantidad: Int,
    @SerializedName("precio_unitario") val precioUnitario: Double
)
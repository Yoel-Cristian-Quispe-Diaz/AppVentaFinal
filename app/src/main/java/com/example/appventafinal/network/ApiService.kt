package com.example.appventafinal.network

import com.example.appventafinal.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    // Usuarios
    @POST("usuarios/")
    fun createUsuario(@Body usuario: UsuarioCreate): Call<Usuario>

    @GET("usuarios/{id}")
    fun getUsuario(@Path("id") id: Int): Call<Usuario>

    @GET("usuarios/")
    fun getUsuarios(): Call<List<Usuario>>

    @PUT("usuarios/{id}")
    fun updateUsuario(@Path("id") id: Int, @Body usuario: UsuarioCreate): Call<Usuario>

    @DELETE("usuarios/{id}")
    fun deleteUsuario(@Path("id") id: Int): Call<Usuario>

    // Categorías
    @POST("categorias/")
    fun createCategoria(@Body categoria: CategoriaCreate): Call<Categoria>

    @GET("categorias/{id}")
    fun getCategoria(@Path("id") id: Int): Call<Categoria>

    @GET("categorias/")
    fun getCategorias(): Call<List<Categoria>>

    @PUT("categorias/{id}")
    fun updateCategoria(@Path("id") id: Int, @Body categoria: CategoriaCreate): Call<Categoria>

    @DELETE("categorias/{id}")
    fun deleteCategoria(@Path("id") id: Int): Call<Categoria>

    // Productos
    @POST("productos/")
    fun createProducto(@Body producto: ProductoCreate): Call<Producto>

    @GET("productos/{id}")
    fun getProducto(@Path("id") id: Int): Call<Producto>

    @GET("productos/")
    fun getProductos(): Call<List<Producto>>

    @PUT("productos/{id}")
    fun updateProducto(@Path("id") id: Int, @Body producto: ProductoCreate): Call<Producto>

    @DELETE("productos/{id}")
    fun deleteProducto(@Path("id") id: Int): Call<Producto>

    // Ventas
    @POST("ventas/")
    fun createVenta(@Body venta: VentaCreate): Call<Venta>

    @GET("ventas/{id}")
    fun getVenta(@Path("id") id: Int): Call<Venta>

    @GET("ventas/")
    fun getVentas(): Call<List<Venta>>

    @PUT("ventas/{id}")
    fun updateVenta(@Path("id") id: Int, @Body venta: VentaCreate): Call<Venta>

    @DELETE("ventas/{id}")
    fun deleteVenta(@Path("id") id: Int): Call<Venta>

    // Detalle Venta
    @POST("detalle_venta/")
    fun createDetalleVenta(@Body detalle: DetalleVentaCreate): Call<DetalleVenta>

    @GET("detalle_venta/{id}")
    fun getDetalleVenta(@Path("id") id: Int): Call<DetalleVenta>

    @GET("detalle_venta/")
    fun getDetallesVenta(): Call<List<DetalleVenta>>

    @PUT("detalle_venta/{id}")
    fun updateDetalleVenta(@Path("id") id: Int, @Body detalle: DetalleVentaCreate): Call<DetalleVenta>

    @DELETE("detalle_venta/{id}")
    fun deleteDetalleVenta(@Path("id") id: Int): Call<DetalleVenta>
}
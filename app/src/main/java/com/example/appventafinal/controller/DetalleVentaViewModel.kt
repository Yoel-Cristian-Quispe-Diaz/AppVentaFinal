package com.example.appventafinal.controller

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appventafinal.model.DetalleVenta
import com.example.appventafinal.model.DetalleVentaCreate
import com.example.appventafinal.model.Producto
import com.example.appventafinal.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetalleVentaViewModel : ViewModel() {
    private val _detallesVenta = MutableLiveData<List<DetalleVenta>>()
    val detallesVenta: LiveData<List<DetalleVenta>> get() = _detallesVenta

    private val _productos = MutableLiveData<List<Producto>>()
    val productos: LiveData<List<Producto>> get() = _productos

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchDetallesVenta() {
        RetrofitClient.apiService.getDetallesVenta().enqueue(object : Callback<List<DetalleVenta>> {
            override fun onResponse(call: Call<List<DetalleVenta>>, response: Response<List<DetalleVenta>>) {
                if (response.isSuccessful) {
                    _detallesVenta.postValue(response.body())
                } else {
                    _error.postValue("Error al obtener detalles de venta: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<DetalleVenta>>, t: Throwable) {
                _error.postValue("Error de red: ${t.message}")
            }
        })
    }

    fun fetchProductos() {
        RetrofitClient.apiService.getProductos().enqueue(object : Callback<List<Producto>> {
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                if (response.isSuccessful) {
                    _productos.postValue(response.body())
                } else {
                    _error.postValue("Error al obtener productos: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                _error.postValue("Error de red: ${t.message}")
            }
        })
    }

    fun createDetalleVenta(detalle: DetalleVentaCreate) {
        RetrofitClient.apiService.createDetalleVenta(detalle).enqueue(object : Callback<DetalleVenta> {
            override fun onResponse(call: Call<DetalleVenta>, response: Response<DetalleVenta>) {
                if (response.isSuccessful) {
                    fetchDetallesVenta() // Refrescar lista
                } else {
                    _error.postValue("Error al crear detalle de venta: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetalleVenta>, t: Throwable) {
                _error.postValue("Error de red: ${t.message}")
            }
        })
    }

    fun updateDetalleVenta(idDetalle: Int, detalle: DetalleVentaCreate) {
        RetrofitClient.apiService.updateDetalleVenta(idDetalle, detalle).enqueue(object : Callback<DetalleVenta> {
            override fun onResponse(call: Call<DetalleVenta>, response: Response<DetalleVenta>) {
                if (response.isSuccessful) {
                    fetchDetallesVenta() // Refrescar lista
                } else {
                    _error.postValue("Error al actualizar detalle de venta: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetalleVenta>, t: Throwable) {
                _error.postValue("Error de red: ${t.message}")
            }
        })
    }

    fun deleteDetalleVenta(idDetalle: Int) {
        RetrofitClient.apiService.deleteDetalleVenta(idDetalle).enqueue(object : Callback<DetalleVenta> {
            override fun onResponse(call: Call<DetalleVenta>, response: Response<DetalleVenta>) {
                if (response.isSuccessful) {
                    fetchDetallesVenta() // Refrescar lista
                } else {
                    _error.postValue("Error al eliminar detalle de venta: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetalleVenta>, t: Throwable) {
                _error.postValue("Error de red: ${t.message}")
            }
        })
    }
}
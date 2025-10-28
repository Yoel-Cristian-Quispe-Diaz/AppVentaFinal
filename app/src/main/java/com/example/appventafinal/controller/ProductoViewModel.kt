package com.example.appventafinal.controller

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appventafinal.model.Producto
import com.example.appventafinal.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductoViewModel : ViewModel() {
    private val _productos = MutableLiveData<List<Producto>>()
    val productos: LiveData<List<Producto>> get() = _productos

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchProductos() {
        RetrofitClient.apiService.getProductos().enqueue(object : Callback<List<Producto>> {
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                if (response.isSuccessful) {
                    _productos.postValue(response.body())
                } else {
                    _error.postValue("Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                _error.postValue("Error: ${t.message}")
            }
        })
    }

    fun createProducto(producto: com.example.appventafinal.model.ProductoCreate) {
        RetrofitClient.apiService.createProducto(producto).enqueue(object : Callback<Producto> {
            override fun onResponse(call: Call<Producto>, response: Response<Producto>) {
                if (response.isSuccessful) {
                    fetchProductos()
                } else {
                    _error.postValue("Error al crear producto: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Producto>, t: Throwable) {
                _error.postValue("Error de red: ${t.message}")
            }
        })
    }

    fun updateProducto(id: Int, producto: com.example.appventafinal.model.ProductoCreate) {
        RetrofitClient.apiService.updateProducto(id, producto).enqueue(object : Callback<Producto> {
            override fun onResponse(call: Call<Producto>, response: Response<Producto>) {
                if (response.isSuccessful) {
                    fetchProductos()
                } else {
                    _error.postValue("Error al actualizar producto: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Producto>, t: Throwable) {
                _error.postValue("Error de red: ${t.message}")
            }
        })
    }

    fun deleteProducto(id: Int) {
        RetrofitClient.apiService.deleteProducto(id).enqueue(object : Callback<Producto> {
            override fun onResponse(call: Call<Producto>, response: Response<Producto>) {
                if (response.isSuccessful) {
                    fetchProductos()
                } else {
                    _error.postValue("Error al eliminar producto: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Producto>, t: Throwable) {
                _error.postValue("Error de red: ${t.message}")
            }
        })
    }
}
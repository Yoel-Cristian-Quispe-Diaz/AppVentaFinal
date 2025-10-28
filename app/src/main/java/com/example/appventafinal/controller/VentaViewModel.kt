package com.example.appventafinal.controller

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appventafinal.model.Venta
import com.example.appventafinal.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VentaViewModel : ViewModel() {
    private val _ventas = MutableLiveData<List<Venta>>()
    val ventas: LiveData<List<Venta>> get() = _ventas

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchVentas() {
        RetrofitClient.apiService.getVentas().enqueue(object : Callback<List<Venta>> {
            override fun onResponse(call: Call<List<Venta>>, response: Response<List<Venta>>) {
                if (response.isSuccessful) {
                    _ventas.postValue(response.body())
                } else {
                    _error.postValue("Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Venta>>, t: Throwable) {
                _error.postValue("Error: ${t.message}")
            }
        })
    }

    fun createVenta(venta: com.example.appventafinal.model.VentaCreate) {
        RetrofitClient.apiService.createVenta(venta).enqueue(object : Callback<Venta> {
            override fun onResponse(call: Call<Venta>, response: Response<Venta>) {
                if (response.isSuccessful) {
                    fetchVentas()
                } else {
                    _error.postValue("Error al crear venta: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Venta>, t: Throwable) {
                _error.postValue("Error de red: ${t.message}")
            }
        })
    }
}
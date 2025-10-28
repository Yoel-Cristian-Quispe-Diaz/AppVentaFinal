package com.example.appventafinal.controller

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appventafinal.model.Categoria
import com.example.appventafinal.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriaViewModel : ViewModel() {
    private val _categorias = MutableLiveData<List<Categoria>>()
    val categorias: LiveData<List<Categoria>> get() = _categorias

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchCategorias() {
        RetrofitClient.apiService.getCategorias().enqueue(object : Callback<List<Categoria>> {
            override fun onResponse(call: Call<List<Categoria>>, response: Response<List<Categoria>>) {
                if (response.isSuccessful) {
                    _categorias.postValue(response.body())
                } else {
                    _error.postValue("Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Categoria>>, t: Throwable) {
                _error.postValue("Error: ${t.message}")
            }
        })
    }

    fun createCategoria(categoria: com.example.appventafinal.model.CategoriaCreate) {
        RetrofitClient.apiService.createCategoria(categoria).enqueue(object : Callback<Categoria> {
            override fun onResponse(call: Call<Categoria>, response: Response<Categoria>) {
                if (response.isSuccessful) {
                    fetchCategorias()
                } else {
                    _error.postValue("Error al crear categoría: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Categoria>, t: Throwable) {
                _error.postValue("Error de red: ${t.message}")
            }
        })
    }
}
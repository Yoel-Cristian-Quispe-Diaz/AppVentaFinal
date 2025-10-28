package com.example.appventafinal.controller

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appventafinal.model.Usuario
import com.example.appventafinal.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsuarioViewModel : ViewModel() {
    private val _usuarios = MutableLiveData<List<Usuario>>()
    val usuarios: LiveData<List<Usuario>> get() = _usuarios

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchUsuarios() {
        RetrofitClient.apiService.getUsuarios().enqueue(object : Callback<List<Usuario>> {
            override fun onResponse(call: Call<List<Usuario>>, response: Response<List<Usuario>>) {
                if (response.isSuccessful) {
                    _usuarios.postValue(response.body())
                } else {
                    _error.postValue("Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Usuario>>, t: Throwable) {
                _error.postValue("Error: ${t.message}")
            }
        })
    }

    fun createUsuario(usuario: com.example.appventafinal.model.UsuarioCreate) {
        RetrofitClient.apiService.createUsuario(usuario).enqueue(object : Callback<Usuario> {
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                if (response.isSuccessful) {
                    fetchUsuarios()
                } else {
                    _error.postValue("Error al crear usuario: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                _error.postValue("Error de red: ${t.message}")
            }
        })
    }

    fun updateUsuario(id: Int, usuario: com.example.appventafinal.model.UsuarioCreate) {
        RetrofitClient.apiService.updateUsuario(id, usuario).enqueue(object : Callback<Usuario> {
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                if (response.isSuccessful) {
                    fetchUsuarios()
                } else {
                    _error.postValue("Error al actualizar usuario: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                _error.postValue("Error de red: ${t.message}")
            }
        })
    }

    fun deleteUsuario(id: Int) {
        RetrofitClient.apiService.deleteUsuario(id).enqueue(object : Callback<Usuario> {
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                if (response.isSuccessful) {
                    fetchUsuarios()
                } else {
                    _error.postValue("Error al eliminar usuario: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                _error.postValue("Error de red: ${t.message}")
            }
        })
    }
}
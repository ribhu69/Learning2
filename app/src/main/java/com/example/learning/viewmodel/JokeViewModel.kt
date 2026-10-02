package com.example.learning.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learning.model.Joke
import com.example.learning.network.NetworkModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class JokeViewModel : ViewModel() {

    private val api = NetworkModule.api

    private val _jokes = MutableStateFlow<List<Joke>>(emptyList())
    val jokes: StateFlow<List<Joke>> = _jokes.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun fetchRandomJoke() {
        if (_isLoading.value) return

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = api.getRandomJoke()
                _jokes.value = _jokes.value + Joke(id = response.id, value = response.value)
            } catch (e: IOException) {
                _errorMessage.value = "Network error, check your connection."
            } catch (e: HttpException) {
                _errorMessage.value = "Server error (${e.code()})."
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearAllJokes() {
        _jokes.value = emptyList()
    }
}

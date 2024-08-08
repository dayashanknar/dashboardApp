package com.gamstrain.oppeningapp.utils


sealed class NetworkState<out T> {
    data class Success<out T>(val data: T) : NetworkState<T>()
    data class Error(val exception: Exception) : NetworkState<Nothing>()
    object Loading : NetworkState<Nothing>()
}

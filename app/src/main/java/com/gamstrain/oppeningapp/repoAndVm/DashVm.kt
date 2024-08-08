package com.gamstrain.oppeningapp.repoAndVm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gamstrain.oppeningapp.models.DashboardResponse
import com.gamstrain.oppeningapp.utils.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashVm @Inject constructor(private val repo: DashRepo) : ViewModel() {


    private val _getDash = MutableLiveData<NetworkState<DashboardResponse>>()
    val getDashState: LiveData<NetworkState<DashboardResponse>> = _getDash

    fun getDash(idToken: String) {
        _getDash.value = NetworkState.Loading
        viewModelScope.launch {
            try {
                val response = repo.getDashboard(idToken)
                if (response.isSuccessful) {
                    _getDash.postValue(NetworkState.Success(response.body()!!))
                } else {
                    _getDash.postValue(
                        NetworkState.Error(
                            Exception(
                                response.errorBody()?.string()
                            )
                        )
                    )
                }
            } catch (e: Exception) {
                _getDash.postValue(NetworkState.Error(e))
            }
        }
    }

}
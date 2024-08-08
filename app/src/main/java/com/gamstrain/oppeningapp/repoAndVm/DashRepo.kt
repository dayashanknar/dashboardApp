package com.gamstrain.oppeningapp.repoAndVm

import com.gamstrain.oppeningapp.models.DashboardResponse
import com.gamstrain.oppeningapp.service.ApiService
import retrofit2.Response
import javax.inject.Inject

class DashRepo @Inject constructor(private val api: ApiService) {

    suspend fun getDashboard(
        idToken: String
    ): Response<DashboardResponse> {
        return api.getDashboard("Bearer $idToken")
    }
}
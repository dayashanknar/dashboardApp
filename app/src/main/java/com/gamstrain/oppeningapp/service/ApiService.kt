package com.gamstrain.oppeningapp.service

import com.gamstrain.oppeningapp.models.DashboardResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {
    @GET("/dashboardNew")
    suspend fun getDashboard(
        @Header("Authorization") idToken: String
    ): Response<DashboardResponse>

    //TODO: we can implement POST method here, but no instructions were given to implement in the assignment

}
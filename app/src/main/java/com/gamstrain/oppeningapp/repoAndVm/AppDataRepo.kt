package com.gamstrain.oppeningapp.repoAndVm

import android.view.View.GONE
import androidx.datastore.core.DataStore
import com.gamstrain.oppeningapp.localData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class AppDataRepo @Inject constructor(private val dataStore: DataStore<localData>) {

    suspend fun updateAuthToken(token: String) {
        dataStore.updateData { currentData ->
            currentData.toBuilder().setAuthToken(token).build()
        }
    }

    fun getAuthToken(): Flow<String?> {
        return dataStore.data.map { it.authToken }
    }
}

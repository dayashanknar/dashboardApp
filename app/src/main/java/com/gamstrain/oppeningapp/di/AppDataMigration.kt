package com.gamstrain.oppeningapp.di

import android.content.Context
import androidx.datastore.migrations.SharedPreferencesMigration
import androidx.datastore.migrations.SharedPreferencesView
import com.gamstrain.oppeningapp.localData


const val USER_PREFERENCES_NAME = "user_preferences"
fun appDataMigrations(context: Context): List<SharedPreferencesMigration<localData>> {
    return listOf(
        SharedPreferencesMigration(
            context, USER_PREFERENCES_NAME
        ) { sharedPrefs: SharedPreferencesView, currentData: localData ->
            val authToken = sharedPrefs.getString("auth_token", "")

            val newData = currentData.toBuilder()
                .clear()
                .setAuthToken(authToken)
                .build()
            newData
        }
    )
}
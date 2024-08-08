package com.gamstrain.oppeningapp.Constants

import android.content.Context
import android.widget.Toast

object Constants {
    const val API_URL = "https://api.inopenapp.com/api/v1"
    const val DATA_STORE_FILE_NAME = "user_prefs.pb"


    fun showToast(context: Context, message:String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
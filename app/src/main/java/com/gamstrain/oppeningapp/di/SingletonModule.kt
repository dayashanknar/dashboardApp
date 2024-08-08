package com.gamstrain.oppeningapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStoreFile
import com.gamstrain.oppeningapp.Constants.Constants.API_URL
import com.gamstrain.oppeningapp.Constants.Constants.DATA_STORE_FILE_NAME
import com.gamstrain.oppeningapp.localData
import com.gamstrain.oppeningapp.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object SingletonModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val cacheSize = 2L * 1024 * 1024 * 1024 // 2gb
        val cache = Cache(context.cacheDir, cacheSize)

        return OkHttpClient.Builder().connectTimeout(80, TimeUnit.SECONDS)
            .readTimeout(130, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.MINUTES).cache(cache)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(API_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun provideSendDeviceToken(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideProtoDataStore(@ApplicationContext appContext: Context): DataStore<localData> {
        return DataStoreFactory.create(
            produceFile = { appContext.dataStoreFile(DATA_STORE_FILE_NAME) },
            serializer = AppDataSerializer,
            corruptionHandler = ReplaceFileCorruptionHandler(produceNewData = { localData.getDefaultInstance() }),
            migrations = appDataMigrations(appContext),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        )
    }

}

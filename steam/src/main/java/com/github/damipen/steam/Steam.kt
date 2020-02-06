package com.github.damipen.steam

import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.net.ssl.HostnameVerifier

class Steam(private val key: String) {

    private val storeService: IStoreService

    init {
        val okhttp = OkHttpClient()
            .newBuilder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .build()
        storeService = Retrofit.Builder()
            .client(okhttp)
            .addConverterFactory(JacksonConverterFactory.create())
            .baseUrl("https://api.steampowered.com/")
            .build()
            .create(IStoreService::class.java)
    }

    suspend fun getAppList(): String {
        return storeService.getAppList(key).body()!!
    }

}
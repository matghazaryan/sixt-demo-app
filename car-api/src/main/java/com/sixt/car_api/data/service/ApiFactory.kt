package com.sixt.car_api.data.service

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sixt.car_api.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit


fun getDefaultApi(baseUrl: String, apiKey: String): CarApi {
    return createDefaultRetrofit(baseUrl, apiKey).create(CarApi::class.java)
}

private fun createDefaultRetrofit(baseUrl: String, apiKey: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(createDefaultClient(apiKey))
        .addConverterFactory(createDefaultConverter())
        .build()
}

private fun createDefaultConverter(): Converter.Factory {
    return Json {
        ignoreUnknownKeys = true
    }.asConverterFactory("application/json".toMediaType())
}

private fun createDefaultClient(apiKey: String): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(ApiKeyInjectorInterceptor(apiKey))
        .addInterceptor(createLoggerInterceptor())
        .build()
}

private class ApiKeyInjectorInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url: HttpUrl = request.url.newBuilder().addQueryParameter("key", apiKey).build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}

private fun createLoggerInterceptor(): Interceptor {
    return HttpLoggingInterceptor().setLevel(
        if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
    )
}

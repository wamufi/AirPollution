package com.wamufi.airpollution.api

import android.util.Log
import com.wamufi.airpollution.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AirKoreaClient {
    companion object {
        private const val BASE_URL = "http://apis.data.go.kr"
        private const val SERVICE_KEY = BuildConfig.SERVICE_KEY

        fun create(): AirKoreaService {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val keyInterceptor = Interceptor {
                val url = it.request().url.newBuilder().addQueryParameter("serviceKey", SERVICE_KEY).build()
                Log.v("servicekey", url.toString())
                val request = it.request().newBuilder().url(url)
                return@Interceptor it.proceed(request.build())
            }

            val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).addInterceptor(keyInterceptor).build()

//            return Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(TikXmlConverterFactory.create(TikXml.Builder().exceptionOnUnreadXml(false).build())).build()
            return Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create()).build()
                .create(AirKoreaService::class.java)
        }
    }
}

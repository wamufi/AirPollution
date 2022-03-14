package com.wamufi.airpollution.api

import com.wamufi.airpollution.BuildConfig
import com.wamufi.airpollution.utils.Logger
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

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
                Logger.v(url.toString())
                val request = it.request().newBuilder().url(url)
                return@Interceptor it.proceed(request.build())
            }

            val client = OkHttpClient.Builder()
            client.connectTimeout(60, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS)
            client.addInterceptor(keyInterceptor).addInterceptor(loggingInterceptor)

            val retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(client.build())
            retrofit.addConverterFactory(GsonConverterFactory.create())

//            return Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(TikXmlConverterFactory.create(TikXml.Builder().exceptionOnUnreadXml(false).build())).build()
//            return Retrofit.Builder().baseUrl(BASE_URL).client(client.build()).addConverterFactory(GsonConverterFactory.create()).build().create(AirKoreaService::class.java)
            return retrofit.build().create(AirKoreaService::class.java)
        }
    }
}

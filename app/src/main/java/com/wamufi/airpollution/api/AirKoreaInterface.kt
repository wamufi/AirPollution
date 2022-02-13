package com.wamufi.airpollution.api

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import com.wamufi.airpollution.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

interface AirKoreaInterface {

    companion object {
        private const val BASE_URL = "http://openapi.airkorea.or.kr"
        private const val SERVICE_KEY = BuildConfig.SERVICE_KEY

        operator fun invoke(): AirKoreaInterface {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val keyInterceptor = Interceptor {
                val url = it.request().url().newBuilder().addQueryParameter("serviceKey", SERVICE_KEY).build()
                val request = it.request().newBuilder().url(url)
                return@Interceptor it.proceed(request.build())
            }

            val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).addInterceptor(keyInterceptor).build()

            return Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(TikXmlConverterFactory.create(TikXml.Builder().exceptionOnUnreadXml(false).build())).build()
                .create(AirKoreaInterface::class.java)
        }
    }
}
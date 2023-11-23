package com.xiaoyv.anime.garden.api

import com.xiaoyv.blueprint.json.GsonParse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * AnimeApiManager
 *
 * @author why
 * @since 11/18/23
 */
class AnimeApiManager {
    private val baseUrl = "https://answer.xiaoyv.com.cn"

    private val httpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .callTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .baseUrl(baseUrl)
            .build()
    }

    private val animeApi by lazy {
        retrofit.create(AnimeApi::class.java)
    }

    companion object {
        private val instance by lazy { AnimeApiManager() }

        val animeApi: AnimeApi
            get() = instance.animeApi
    }
}
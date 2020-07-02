package com.dobrucali.logcomponent.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
const val API_KEY = "b20d814dbd886c18d47032a930071223"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface ForecastApiService {
    @GET("weather")
    fun getForecast(@Query("q") city: String, @Query("appid") appId: String)
            : Deferred<Forecast>

    @GET("weather")
    fun getForecastCall(@Query("q") city: String, @Query("appid") appId: String)
            : Call<Forecast>

}

object DirectionApi {
    val retrofitService: ForecastApiService by lazy {
        retrofit.create(ForecastApiService::class.java)
    }
}

package com.dobrucali.logcomponent.main

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dobrucali.logcomponent.di.ILogger
import com.dobrucali.logcomponent.R
import com.dobrucali.logcomponent.network.API_KEY
import com.dobrucali.logcomponent.network.DirectionApi
import com.dobrucali.logcomponent.network.Forecast
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel (
    private val logger: ILogger
): ViewModel() {

    private val _lastChosenDirection = MutableLiveData<Direction>()
    val lastChosenDirection: LiveData<Direction>
        get() = _lastChosenDirection

    private val _forecast = MutableLiveData<Forecast>()
    val forecast: LiveData<Forecast>
        get() = _forecast

    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getForecast("Istanbul")
        //getForecastAsynchronous("London")
        getForecastSynchronous("Barcelona")
    }

    fun chooseDirection(view: View) {
        _lastChosenDirection.value = when (view.id) {
            R.id.west_button -> Direction.WEST
            R.id.east_button -> Direction.EAST
            R.id.north_button -> Direction.NORTH
            R.id.south_button -> Direction.SOUTH
            else -> null
        }
    }

    private fun getForecast(city: String) {
        coroutineScope.launch(Dispatchers.Main) {
            val getPropertiesDeferred = DirectionApi.retrofitService.getForecast(city,
                API_KEY
            )
            try {
                _status.value = "LOADING"
                val forecast = getPropertiesDeferred.await()
                _forecast.value = forecast
                logger.d("viewModel getForecast success")
                _status.value = "SUCCESSS"
            } catch (e: Exception) {
                _status.value = "ERROR"
            }
        }
    }

    private fun getForecastAsynchronous(city: String) {
        coroutineScope.launch(Dispatchers.Main) {
            _status.value = "LOADING"
            val call = DirectionApi.retrofitService.getForecastCall(city,
                API_KEY
            )
            call.enqueue(object : Callback<Forecast> {
                override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                    if (response.isSuccessful) {
                        _status.value = "SUCCESSS"
                    } else {
                        _status.value = "ERROR"
                    }
                }

                override fun onFailure(call: Call<Forecast>, t: Throwable) {
                    _status.value = "ERROR"
                }
            })

        }
    }

    private fun getForecastSynchronous(city: String) {
        coroutineScope.launch(Dispatchers.Default) {
            val service = DirectionApi.retrofitService
            val callSync: Call<Forecast> = service.getForecastCall(city,
                API_KEY
            )
            try {
                val response: Response<Forecast> = callSync.execute()
                withContext(Dispatchers.Main) {
                    _forecast.value = response.body()
                    logger.d( "viewModel getForecastSynchronous success")
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    enum class Direction {
        EAST,
        WEST,
        NORTH,
        SOUTH
    }

}
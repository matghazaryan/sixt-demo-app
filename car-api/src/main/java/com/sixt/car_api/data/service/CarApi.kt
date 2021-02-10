package com.sixt.car_api.data.service

import com.sixt.car_api.data.dto.CarDto
import retrofit2.Call
import retrofit2.http.GET

interface CarApi {
    @GET("codingtask/cars")
    fun getCars(): Call<List<CarDto>>
}
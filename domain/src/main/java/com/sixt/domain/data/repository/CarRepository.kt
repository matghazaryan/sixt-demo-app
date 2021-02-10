package com.sixt.domain.data.repository

import com.sixt.car_api.response.CarResponse
import com.sixt.domain.data.responce.ResourceResponse

interface CarRepository {

    suspend fun getCars(): ResourceResponse<CarResponse>
}
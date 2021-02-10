package com.sixt.car_api.data.repository

import com.sixt.car_api.data.service.CarApi
import com.sixt.car_api.mapper.CarMapper
import com.sixt.car_api.response.CarResponse
import com.sixt.domain.data.repository.CarRepository
import com.sixt.domain.data.responce.ResourceResponse
import com.sixt.utils.extensions.awaitApiResponse

class SixtRepository(private val api: CarApi, private val mapper: CarMapper): CarRepository {
    override suspend fun getCars(): ResourceResponse<CarResponse> {
        return api.getCars().awaitApiResponse().map(mapper::mapCarDtoToEntity)
    }
}
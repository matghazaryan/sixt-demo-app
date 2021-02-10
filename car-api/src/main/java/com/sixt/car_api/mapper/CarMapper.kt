package com.sixt.car_api.mapper

import com.sixt.car_api.data.dto.CarDto
import com.sixt.car_api.response.CarResponse
import com.sixt.domain.data.entity.Car

class CarMapper {

    fun mapCarDtoToEntity(dto: List<CarDto>): CarResponse {
        return CarResponse(mapDataDtoToEntity(dto))
    }

    private fun mapDataDtoToEntity(data: List<CarDto>): List<Car> {
        return data.map(::mapGifDtoToEntity)
    }

    private fun mapGifDtoToEntity(dto: CarDto): Car {
        return Car(
            dto.id,
            dto.modelName,
            dto.name,
            dto.licensePlate,
            dto.make,
            dto.latitude,
            dto.longitude,
            dto.carImageUrl,
            dto.transmission,
            dto.fuelType,
            dto.fuelLevel
        )
    }
}
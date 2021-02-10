package com.sixt.car

import com.sixt.domain.data.entity.Car
import com.sixt.domain.data.repository.CarRepository
import com.sixt.domain.data.responce.ResourceResponse
import javax.inject.Inject

class GetCarUseCase @Inject constructor(private val carRepository: CarRepository) {

    suspend operator fun invoke(): ResourceResponse<List<ItemData>> {
        return carRepository.getCars().map { it.cars.map(::mapItems) }
    }

    private fun mapItems(car: Car): ItemData {
        return ItemData(
            car.id,
            car.modelName,
            car.name,
            car.licensePlate,
            car.make,
            car.latitude,
            car.longitude,
            car.carImageUrl,
            car.transmission,
            car.fuelType,
            car.fuelLevel
        )
    }
}
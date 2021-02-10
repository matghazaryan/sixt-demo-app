package com.sixt.car_api.data.dto

import kotlinx.serialization.Serializable

@Serializable
 class CarDto(
    val id: String?,
    val modelName: String?,
    val name: String?,
    val licensePlate:String?,
    val make: String?,
    val latitude: Double?,
    val longitude: Double?,
    val carImageUrl: String?,
    val transmission: String?,
    val fuelType: String?,
    val fuelLevel: Double?
)
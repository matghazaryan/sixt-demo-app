package com.sixt.carDetails

import androidx.lifecycle.ViewModel
import com.sixt.car.ItemData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CarDetailsViewModel @Inject constructor(private val carDetails: CarDetails):
    ViewModel() {
    private val dataInternal = MutableStateFlow(carDetails)
    val data = dataInternal.asStateFlow()
}

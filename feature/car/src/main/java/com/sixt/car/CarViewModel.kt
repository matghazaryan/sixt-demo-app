package com.sixt.car

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sixt.domain.data.responce.ResourceSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarViewModel @Inject constructor(private val getCars: GetCarUseCase): ViewModel(){

    sealed class Command {
        object GeneralError : Command()
        class CarDetail(
            val latitude: Double,
            val longitude: Double,
            val name: String
        ) : Command()
    }
    private val dataInternal = MutableStateFlow<List<ItemData>>(emptyList())
    val data = dataInternal.asStateFlow()

    private val commandInternal = Channel<Command>()
    val command: Flow<Command> = commandInternal.receiveAsFlow()

    init {
        loadData()
    }

    fun onItemClicked(data: ItemData) {
        dispatchCommand(Command.CarDetail(data.latitude!!, data.longitude!!,data.name!!))
    }

    private fun loadData() = viewModelScope.launch {
        val response = getCars()
        if (response !is ResourceSuccess) {
            dispatchCommand(Command.GeneralError)
            return@launch
        }

        updateData(response.data)
    }

    private fun updateData(values: List<ItemData>) {
        dataInternal.value = values
    }

    private fun dispatchCommand(command: Command) = viewModelScope.launch {
        commandInternal.send(command)
    }
}
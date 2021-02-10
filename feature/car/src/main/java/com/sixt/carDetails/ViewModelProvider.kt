package com.sixt.carDetails

import androidx.lifecycle.SavedStateHandle
import com.sixt.car.ItemData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelProvider {
    @Provides
    @ViewModelScoped
    fun provideViewModel(handle: SavedStateHandle): CarDetails {
        return CarDetails(handle["latitude"]!!, handle["longitude"]!!,handle["name"]!!)
    }

}
package com.sixt.carDetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.sixt.car.R

class CarDetailsFragment : Fragment(R.layout.fragment_car_details) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val showAll = arguments?.getBoolean("showAll")
        if (showAll == true){
            showAllPinsOnTheMap()
        } else {
            val lat = arguments?.getDouble("latitude")
            val lng = arguments?.getDouble("longitude")
            val name = arguments?.getString("name")
            val carDetails = CarDetails(lat!!, lng!!, name!!)
            showOnePinOnTheMap(carDetails)
        }
    }

    private fun showOnePinOnTheMap(carDetails: CarDetails) {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.f_map) as SupportMapFragment?
        mapFragment!!.getMapAsync { map ->
            map.mapType = GoogleMap.MAP_TYPE_NORMAL
            map.clear()

            zoomIn(map, carDetails, true)
            addMarkersOnMap(map, carDetails)
        }
    }

    private fun showAllPinsOnTheMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.f_map) as SupportMapFragment?
        mapFragment!!.getMapAsync { map ->

            map.mapType = GoogleMap.MAP_TYPE_NORMAL
            map.clear()
        }
    }

    private fun zoomIn(map: GoogleMap, car: CarDetails, isOnePin : Boolean = false) {
        var zoom = 10f
        if (isOnePin) zoom = 18f

        val googlePlex = CameraPosition.builder()
            .zoom(zoom)
            .bearing(0f)
            .tilt(45f)
            .target(LatLng(car.latitude, car.longitude))
            .build()
        map.animateCamera(
            CameraUpdateFactory.newCameraPosition(googlePlex),
            5000,
            null
        )
    }


    private fun addMarkersOnMap(map: GoogleMap, car: CarDetails) {
        map.addMarker(
            MarkerOptions()
                .position(LatLng(car.latitude, car.longitude))
                .title(car.name)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)))
    }

    companion object {
        fun createInstance(latitude: Double, longitude: Double, name: String): CarDetailsFragment {
            return CarDetailsFragment().apply {
                arguments = Bundle().apply {
                    putDouble("latitude", latitude)
                    putDouble("longitude", longitude)
                    putString("name", name)
                }
            }
        }
    }

}
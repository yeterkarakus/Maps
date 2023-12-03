package com.yeterkarakus.maps

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.IntentSender
import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.yeterkarakus.maps.databinding.FragmentMapsBinding

class MapsFragment : Fragment(),OnMapReadyCallback {
    private var _binding : FragmentMapsBinding? = null
    private val binding get() = _binding!!
    private lateinit var permissionLauncher : ActivityResultLauncher<String>
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var map : GoogleMap
    private lateinit var userLocation : LatLng

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentMapsBinding.inflate(layoutInflater,container,false)
        registerLauncher()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)


    }
    override fun onMapReady(p0: GoogleMap) {
        map = p0

        when {
            ContextCompat.checkSelfPermission(requireContext(), ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED -> {

                getUserLocation()

            }
            ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), ACCESS_FINE_LOCATION) -> {
                Snackbar.make(binding.root,R.string.snackbar_text,Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.give_permission){
                        permissionLauncher.launch(ACCESS_FINE_LOCATION)
                    }.show()
            }
            else -> {
                permissionLauncher.launch(ACCESS_FINE_LOCATION)
            }
        }
    }
    private fun registerLauncher() {
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if(isGranted) {
                if (ContextCompat.checkSelfPermission(requireContext(), ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {

                   getUserLocation()

                }
            }
        }
    }
    @SuppressLint("MissingPermission")
    private fun getUserLocation(){
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000).build()
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client = LocationServices.getSettingsClient(requireActivity())
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            fusedLocationClient.requestLocationUpdates(locationRequest,
                object : LocationCallback(){
                    override fun onLocationResult(p0: LocationResult) {
                        val lastLocation = p0.lastLocation
                        lastLocation?.let {
                            userLocation = LatLng(it.latitude,it.longitude)
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f))
                            map.addMarker(MarkerOptions().title("Konum").position(userLocation))
                        }
                    }
                }, Looper.getMainLooper())

        }.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                try {
                    exception.startResolutionForResult(requireActivity(), 2)
                } catch (sendEx: IntentSender.SendIntentException) {
                    sendEx.localizedMessage
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
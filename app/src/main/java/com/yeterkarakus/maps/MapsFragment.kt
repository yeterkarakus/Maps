package com.yeterkarakus.maps

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.yeterkarakus.maps.databinding.FragmentMapsBinding

class MapsFragment : Fragment(),OnMapReadyCallback {
    private var _binding : FragmentMapsBinding? = null
    private val binding get() = _binding!!
    private lateinit var permissionLauncher : ActivityResultLauncher<String>
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var map : GoogleMap

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
        fusedLocationClient= LocationServices.getFusedLocationProviderClient(requireContext())
        when {
            ContextCompat.checkSelfPermission(requireContext(), ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED -> {
                fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    location?.let {
                        val userLocation = LatLng(it.latitude, it.longitude)
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f))
                        map.addMarker(MarkerOptions().title("Konum").position(userLocation))
                    }
                }
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
                    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                        location?.let {
                            val userLocation = LatLng(it.latitude, it.longitude)
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f))
                            map.addMarker(MarkerOptions().title("Konum").position(userLocation))
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
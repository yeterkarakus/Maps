package com.yeterkarakus.maps.maps

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
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.yeterkarakus.maps.R
import com.yeterkarakus.maps.util.Status
import com.yeterkarakus.maps.databinding.FragmentMapsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsFragment : Fragment(),OnMapReadyCallback {
    private var _binding : FragmentMapsBinding? = null
    private val binding get() = _binding!!
    private lateinit var permissionLauncher : ActivityResultLauncher<String>
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var map : GoogleMap
    private lateinit var userLocation : LatLng
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private lateinit var mapsViewModel: MapsViewModel
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

        mapsViewModel = ViewModelProvider(this)[MapsViewModel::class.java]
        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 120000).build()
        locationCallback = object : LocationCallback(){
            override fun onLocationResult(p0: LocationResult) {
                val lastLocation = p0.lastLocation
                lastLocation?.let {
                    userLocation = LatLng(it.latitude,it.longitude)
                }
            }
        }

        binding.searchText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                observeData()

                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false

        }

    }

    override fun onMapReady(p0: GoogleMap) {

        map = p0
        when {
            ContextCompat.checkSelfPermission(requireContext(), ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED -> {

                getUserLocation()

            }
            ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), ACCESS_FINE_LOCATION) -> {
                Snackbar.make(binding.root, R.string.snackbar_text,Snackbar.LENGTH_INDEFINITE)
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

    private fun observeData(){
        mapsViewModel.getData(binding.searchText.text.toString(),userLocation.latitude,userLocation.longitude,"20","tur","tr")
        mapsViewModel.searchList.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                    binding.loadingAnimation.visibility = View.VISIBLE
                    binding.map.visibility = View.GONE


                }

                Status.SUCCESS -> {
                    binding.loadingAnimation.visibility = View.GONE
                    binding.map.visibility = View.VISIBLE
                    it.data?.let { dataResult ->
                        for (x in dataResult.data) {
                            val latLng = LatLng(x.latitude, x.longitude)

                            map.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                            map.addMarker(
                                MarkerOptions().icon(
                                    BitmapDescriptorFactory.defaultMarker(
                                        BitmapDescriptorFactory.HUE_AZURE
                                    )
                                ).title(x.name).position(latLng)
                            )
                        }
                    }

                }

                Status.ERROR -> {
                    binding.loadingAnimation.visibility = View.GONE
                    binding.map.visibility = View.VISIBLE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }


    @SuppressLint("MissingPermission")
    override fun onResume() {
        super.onResume()
        fusedLocationClient.requestLocationUpdates(locationRequest,locationCallback, Looper.getMainLooper())
    }

    @SuppressLint("MissingPermission")
    private fun getUserLocation(){
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client = LocationServices.getSettingsClient(requireActivity())
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                userLocation = LatLng(it.latitude,it.longitude)
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 13f))
                map.addMarker(MarkerOptions().title("Konum").position(userLocation))
            }

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

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        map.clear()
    }
}
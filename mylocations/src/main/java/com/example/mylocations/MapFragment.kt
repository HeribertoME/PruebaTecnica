package com.example.mylocations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mylocations.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore

class MapFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private lateinit var googleMap: GoogleMap
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)
        firestore = FirebaseFirestore.getInstance()
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        loadLocations()
    }

    private fun loadLocations() {
        firestore.collection("locations")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val latitude = document.getDouble("latitude")!!
                    val longitude = document.getDouble("longitude")!!
                    val location = LatLng(latitude, longitude)
                    googleMap.addMarker(MarkerOptions().position(location))
                }
                if (!documents.isEmpty) {
                    val firstLocation = documents.first()
                    val latitude = firstLocation.getDouble("latitude")!!
                    val longitude = firstLocation.getDouble("longitude")!!
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), 10f))
                }
            }
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }
}

package com.example.mylocations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mylocations.databinding.FragmentLocationListBinding
import com.example.mylocations.databinding.ItemLocationBinding
import com.google.firebase.firestore.FirebaseFirestore

class LocationListFragment : Fragment() {

    private var _binding: FragmentLocationListBinding? = null
    private val binding get() = _binding!!
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firestore = FirebaseFirestore.getInstance()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        loadLocations()
    }

    private fun loadLocations() {
        firestore.collection("locations")
            .get()
            .addOnSuccessListener { documents ->
                val locations = documents.map { document ->
                    val latitude = document.getDouble("latitude")!!
                    val longitude = document.getDouble("longitude")!!
                    val timestamp = document.getLong("timestamp")!!
                    LocationItem(latitude, longitude, timestamp)
                }
                binding.recyclerView.adapter = LocationAdapter(locations)
            }
    }

    data class LocationItem(val latitude: Double, val longitude: Double, val timestamp: Long)

    class LocationAdapter(private val locations: List<LocationItem>) :
        RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
            val binding = ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return LocationViewHolder(binding)
        }

        override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
            val location = locations[position]
            holder.bind(location)
        }

        override fun getItemCount(): Int = locations.size

        class LocationViewHolder(private val binding: ItemLocationBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun bind(location: LocationItem) {
                binding.latitudeTextView.text = location.latitude.toString()
                binding.longitudeTextView.text = location.longitude.toString()
                binding.timestampTextView.text = android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss", location.timestamp)
            }
        }
    }
}

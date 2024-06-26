package com.example.mylocations

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@HiltWorker
class MyLocationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters
) : CoroutineWorker(context, params) {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val firestore = FirebaseFirestore.getInstance()

    override suspend fun doWork(): Result {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)

        if (ContextCompat.checkSelfPermission(applicationContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(applicationContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("MyLocationWorker", "Location permissions are not granted")
            return Result.failure()
        }

        val location = getLocation() ?: return Result.failure()
        saveLocationToFirestore(location)
        return Result.success()
    }

    private suspend fun getLocation(): Location? = withContext(Dispatchers.IO) {
        try {
            val locationTask = fusedLocationClient.lastLocation
            val location = locationTask.await()
            location
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun saveLocationToFirestore(location: Location) {
        val locationData = hashMapOf(
            "latitude" to location.latitude,
            "longitude" to location.longitude,
            "timestamp" to System.currentTimeMillis()
        )
        firestore.collection("locations")
            .add(locationData)
            .addOnSuccessListener {
                // Location saved
            }
            .addOnFailureListener {
                // Error saving location
            }
    }
}

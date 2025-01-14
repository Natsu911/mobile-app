package kh.edu.rupp.ite.mobileapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kh.edu.rupp.ite.mobileapp.api.LocationApiService
import kh.edu.rupp.ite.mobileapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var locationAdapter: LocationAdapter
    private lateinit var locationApiService: LocationApiService
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://smlp-pub.s3.ap-southeast-1.amazonaws.com/") // API base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        locationApiService = retrofit.create(LocationApiService::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.locationRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.requestRideButton.setOnClickListener { // This is the click listener you're asking about
            val pickup = binding.pickupEditText.text.toString()
            val destination = binding.destinationEditText.text.toString()

            val selectedVehicleId = binding.vehicleTypeRadioGroup.checkedRadioButtonId
            val selectedVehicle = if (selectedVehicleId != -1) {
                view.findViewById<RadioButton>(selectedVehicleId).text.toString()
            } else {
                "No vehicle selected"
            }

            if (pickup.isNotEmpty() && destination.isNotEmpty() && selectedVehicle != "No vehicle selected") {
                binding.progressBar.visibility = View.VISIBLE
                binding.requestRideButton.isEnabled = false

                Handler(Looper.getMainLooper()).postDelayed({
                    binding.progressBar.visibility = View.GONE
                    binding.requestRideButton.isEnabled = true
                    Toast.makeText(
                        requireContext(),
                        "Ride requested from $pickup to $destination by $selectedVehicle",
                        Toast.LENGTH_SHORT
                    ).show()
                }, 2000)
            } else if (selectedVehicle == "No vehicle selected") {
                Toast.makeText(requireContext(), "Please select Vehicle Type", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Please enter pickup and destination", Toast.LENGTH_SHORT).show()
            }
        }

        coroutineScope.launch { // The API call and RecyclerView setup
            try {
                val locationsFromApi = withContext(Dispatchers.IO) {
                    locationApiService.getLocations()
                }

                val locationDataList = if (locationsFromApi.isNotEmpty()) {
                    locationsFromApi.map { apiLocation ->
                        LocationData(R.drawable.location_placeholder, apiLocation.name)
                    }
                } else {
                    listOf(
                        LocationData(R.drawable.location_placeholder, "Location 1"),
                        LocationData(R.drawable.location_placeholder, "Location 2"),
                        LocationData(R.drawable.location_placeholder, "Location 3")
                    )
                }

                locationAdapter = LocationAdapter(locationDataList) { location ->
                    binding.pickupEditText.setText(location.name)
                }
                binding.locationRecyclerView.adapter = locationAdapter

            } catch (e: Exception) {
                Log.e("HomeFragment", "Error fetching locations", e)
                Toast.makeText(requireContext(), "Error fetching locations", Toast.LENGTH_SHORT).show()
                val locationDataList = listOf(
                    LocationData(R.drawable.royalpalace, "Royal Palace"),
                    LocationData(R.drawable.aeonmall, "Aeon Mall"),
                    LocationData(R.drawable.riverside, "River Side")
                )
                locationAdapter = LocationAdapter(locationDataList) { location ->
                    binding.pickupEditText.setText(location.name)
                }
                binding.locationRecyclerView.adapter = locationAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        coroutineScope.cancel()
    }
}
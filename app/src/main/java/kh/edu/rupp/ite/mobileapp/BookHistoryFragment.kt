package kh.edu.rupp.ite.mobileapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.ite.mobileapp.R
import kh.edu.rupp.ite.mobileapp.BookingHistoryAdapter

class BookHistoryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BookingHistoryAdapter
    private val bookingList = mutableListOf<Booking>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_book_history, container, false)

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewBookingHistory)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = BookingHistoryAdapter(bookingList)
        recyclerView.adapter = adapter

        // Load booking data
        loadBookingHistory()

        return view
    }

    private fun loadBookingHistory() {
        // Example data
        bookingList.add(Booking("Royal Place", "2024-12-23", "Completed", "4300៛"))
        bookingList.add(Booking("Aeon Mall", "2024-12-16", "Completed", "9800៛"))
        bookingList.add(Booking("River Side", "2025-11-29", "Cancelled", "Cancelled"))

        adapter.notifyDataSetChanged()
    }

}

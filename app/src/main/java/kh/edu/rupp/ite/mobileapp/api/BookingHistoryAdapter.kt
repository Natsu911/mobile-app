package kh.edu.rupp.ite.mobileapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.ite.mobileapp.R

class BookingHistoryAdapter(private val bookingList: List<Booking>) :
    RecyclerView.Adapter<BookingHistoryAdapter.BookingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_booking, parent, false)
        return BookingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val booking = bookingList[position]
        holder.titleTextView.text = booking.title
        holder.dateTextView.text = booking.date
        holder.statusTextView.text = booking.status
        holder.priceTextView.text = booking.price
    }

    override fun getItemCount(): Int = bookingList.size

    class BookingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.textViewBookingTitle)
        val dateTextView: TextView = itemView.findViewById(R.id.textViewBookingDate)
        val statusTextView: TextView = itemView.findViewById(R.id.textViewBookingStatus)
        val priceTextView: TextView = itemView.findViewById(R.id.textViewBookingPrice)
    }
}

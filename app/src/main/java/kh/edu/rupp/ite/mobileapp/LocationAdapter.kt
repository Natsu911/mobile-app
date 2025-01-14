package kh.edu.rupp.ite.mobileapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kh.edu.rupp.ite.mobileapp.R

class LocationAdapter(
    private val locations: List<LocationData>,
    private val onItemClick: (LocationData) -> Unit
) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.location_image)
        val textView: TextView = itemView.findViewById(R.id.location_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_location, parent, false)
        return LocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = locations[position]
        holder.textView.text = location.name


        Glide.with(holder.itemView.context)
            .load(location.id) // Load placeholder
            .centerCrop()
            .into(holder.imageView)


        holder.itemView.setOnClickListener {
            onItemClick(location)
        }
    }

    override fun getItemCount(): Int = locations.size
}
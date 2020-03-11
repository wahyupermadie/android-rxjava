package com.godohdev.latihanrxjava

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.godohdev.latihanrxjava.data.model.ResponseFlights
import kotlinx.android.synthetic.main.tiket_row.view.airline_name
import kotlinx.android.synthetic.main.tiket_row.view.arrival
import kotlinx.android.synthetic.main.tiket_row.view.departure
import kotlinx.android.synthetic.main.tiket_row.view.duration
import kotlinx.android.synthetic.main.tiket_row.view.loader
import kotlinx.android.synthetic.main.tiket_row.view.logo
import kotlinx.android.synthetic.main.tiket_row.view.number_of_seats
import kotlinx.android.synthetic.main.tiket_row.view.number_of_stops
import kotlinx.android.synthetic.main.tiket_row.view.price

/**
 *
 * Created by Wahyu Permadi on 11/03/20.
 * Android Engineer
 *
 **/

class TicketAdapter(private val context: Context) : RecyclerView.Adapter<TicketAdapter.ViewHolder>(){
    private var ticketList : ArrayList<ResponseFlights> = arrayListOf()

    fun addData(responseFlights: ArrayList<ResponseFlights>){
        this.ticketList.clear()
        this.ticketList = responseFlights
        notifyDataSetChanged()
    }

    fun addPrice(position: Int, responseFlights: ResponseFlights){
        this.ticketList[position] = responseFlights
        notifyItemChanged(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.tiket_row, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return ticketList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ticket = ticketList.get(position);

        Glide.with(context)
            .load(ticket.airline?.logo)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.itemView.logo)

        holder.itemView.airline_name.text = ticket.airline?.name

        holder.itemView.departure.text = ticket.departure + " Dep";
        holder.itemView.arrival.text = ticket.arrival + " Dest";

        holder.itemView.duration.text = ticket.flightNumber
        holder.itemView.duration.append(", " + ticket.duration)
        holder.itemView.number_of_stops.text = ticket.stops.toString() + " Stops";

        if (!TextUtils.isEmpty(ticket.instructions)) {
            holder.itemView.duration.append(", " + ticket.instructions);
        }

        if (ticket.price != null) {
            holder.itemView.price.text = "₹" + String.format("%.0f", ticket.price?.price)
            holder.itemView.number_of_seats.text = ticket.price?.seats.toString() + " Seats";
            holder.itemView.loader.visibility = View.INVISIBLE
        } else {
            holder.itemView.loader.visibility = View.VISIBLE
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }
}
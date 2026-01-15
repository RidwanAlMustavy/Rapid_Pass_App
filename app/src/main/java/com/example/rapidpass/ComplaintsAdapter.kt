package com.example.rapidpass

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ComplaintsAdapter(private val complaints: List<Complain1>) :
    RecyclerView.Adapter<ComplaintsAdapter.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tv: TextView = view.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val v = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return Holder(v)
    }

    override fun getItemCount() = complaints.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val c = complaints[position]
        holder.tv.text =
            "User: ${c.username}\nComplaint: ${c.text}"
    }
}

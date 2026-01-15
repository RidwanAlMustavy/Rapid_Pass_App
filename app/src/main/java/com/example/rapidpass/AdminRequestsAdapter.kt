package com.example.rapidpass

import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference

class AdminRequestsAdapter(
    private val requests: List<AdminRequest>,
    private val usersRef: DatabaseReference,
    private val refresh: () -> Unit
) : RecyclerView.Adapter<AdminRequestsAdapter.Holder>() {

    class Holder(val layout: LinearLayout) : RecyclerView.ViewHolder(layout) {
        val tv: TextView = layout.getChildAt(0) as TextView
        val approve: Button = layout.getChildAt(1) as Button
        val reject: Button = layout.getChildAt(2) as Button
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layout = LinearLayout(parent.context).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(16, 16, 16, 16)
        }

        layout.addView(TextView(parent.context).apply {
            layoutParams = LinearLayout.LayoutParams(0, -2, 1f)
        })

        layout.addView(Button(parent.context).apply { text = "Approve" })
        layout.addView(Button(parent.context).apply { text = "Reject" })

        return Holder(layout)
    }

    override fun getItemCount() = requests.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val r = requests[position]
        holder.tv.text = r.username

        holder.approve.setOnClickListener {
            usersRef.child(r.uid).child("adminRequest").setValue("approved")
            refresh()
        }

        holder.reject.setOnClickListener {
            usersRef.child(r.uid).child("adminRequest").setValue("rejected")
            refresh()
        }
    }
}

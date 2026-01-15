package com.example.rapidpass

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase

class Admin : AppCompatActivity() {

    private lateinit var tvUsers: TextView
    private lateinit var tvTotalRecharge: TextView
    private lateinit var tvComplaints: TextView
    private lateinit var tvAdminRequests: TextView

    private lateinit var rvUsers: RecyclerView
    private lateinit var rvComplaints: RecyclerView
    private lateinit var rvAdminRequests: RecyclerView

    private val db = FirebaseDatabase.getInstance()
    private val usersRef = db.getReference("Users")
    private val complaintsRef = db.getReference("Complaints")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        tvUsers = findViewById(R.id.tvUsers)
        tvTotalRecharge = findViewById(R.id.tvTotalRecharge)
        tvComplaints = findViewById(R.id.tvComplaints)
        tvAdminRequests = findViewById(R.id.tvAdminRequests)

        rvUsers = findViewById(R.id.rvUsers)
        rvComplaints = findViewById(R.id.rvComplaints)
        rvAdminRequests = findViewById(R.id.rvAdminRequests)

        rvUsers.layoutManager = LinearLayoutManager(this)
        rvComplaints.layoutManager = LinearLayoutManager(this)
        rvAdminRequests.layoutManager = LinearLayoutManager(this)

        loadUsers()
        loadComplaints()
        loadAdminRequests()
    }

    private fun loadUsers() {
        usersRef.get().addOnSuccessListener { snapshot ->
            tvUsers.text = "Total Users: ${snapshot.childrenCount}"

            val list = ArrayList<User>()
            var totalRecharge = 0f

            for (snap in snapshot.children) {
                val uid = snap.key ?: continue
                val username = snap.child("username").value?.toString() ?: "Unknown"
                val email = snap.child("email").value?.toString() ?: ""
                val phone = snap.child("phone").value?.toString() ?: ""
                val role = snap.child("role").value?.toString() ?: "user"
                val recharge = snap.child("totalRecharge").getValue(Float::class.java) ?: 0f

                totalRecharge += recharge
                list.add(User(uid, username, email, phone, role, recharge))
            }

            tvTotalRecharge.text = "Total Recharge: $totalRecharge BDT"
            rvUsers.adapter = UsersAdapter(list)
        }
    }

    private fun loadComplaints() {
        complaintsRef.get().addOnSuccessListener { snapshot ->
            tvComplaints.text = "Total Complaints: ${snapshot.childrenCount}"

            val list = ArrayList<Complain1>()

            for (snap in snapshot.children) {
                val user = snap.child("user").value?.toString() ?: "Unknown"
                val text = snap.child("text").value?.toString() ?: ""

                list.add(Complain1( user, text))
            }

            rvComplaints.adapter = ComplaintsAdapter(list)
        }
    }

    private fun loadAdminRequests() {
        usersRef.get().addOnSuccessListener { snapshot ->
            val list = ArrayList<AdminRequest>()

            for (snap in snapshot.children) {
                val request = snap.child("adminRequest").value?.toString()
                if (request == "pending") {
                    val uid = snap.key ?: continue
                    val username = snap.child("username").value?.toString() ?: "Unknown"
                    list.add(AdminRequest(uid, username))
                }
            }

            tvAdminRequests.text = "Admin Requests: ${list.size}"
            rvAdminRequests.adapter =
                AdminRequestsAdapter(list, usersRef) { loadAdminRequests() }
        }
    }
}

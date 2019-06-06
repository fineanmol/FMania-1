package com.example.fmania

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.*
import android.util.Log
import android.widget.ListView

class getDataActivity : AppCompatActivity() {
    lateinit var ref: DatabaseReference
    lateinit var employeeList: MutableList<Data>
    lateinit var listview: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_data)

        employeeList = mutableListOf()
        listview = findViewById(R.id.listview)
        ref = FirebaseDatabase.getInstance().getReference("users")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()) {
                    employeeList.clear()
                    for (e in p0.children) {
                        val employee = e.getValue(Data::class.java)
                        employeeList.add(employee!!)
                    }
                    val adapter = customAdapter(this@getDataActivity, R.layout.users, employeeList)
                    listview.adapter = adapter
                }
            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }
}

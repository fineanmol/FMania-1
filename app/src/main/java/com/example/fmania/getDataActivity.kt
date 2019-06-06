package com.example.fmania

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.*
import android.util.Log

class getDataActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    val TAG="DataRecieved"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_data)
        database = FirebaseDatabase.getInstance().getReference("users")
        val postListener = object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                // Get Post object and use the values to update the UI

                for (d in p0.children){
                    val post = d.getValue(rcv_data::class.java)
                    Log.d(TAG, "Value is: $d")
                    }
                // ...
            }

            override fun onCancelled(p0: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", p0.toException())
                // ...
            }
        }
        database.addValueEventListener(postListener)
    }
}

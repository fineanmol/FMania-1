package com.example.fmania

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*
import com.example.fmania.R
import com.example.fmania.logout
import kotlinx.android.synthetic.main.activity_user_profile.*
import com.google.firebase.database.*
//import jdk.nashorn.internal.runtime.ECMAErrors.getMessage
import com.google.firebase.database.DatabaseError
import java.nio.file.Files.exists
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.util.Log
import android.widget.ListView






class homeActivity : AppCompatActivity() {
    private val currentUser = FirebaseAuth.getInstance().currentUser
    lateinit var ref: DatabaseReference
    lateinit var employeeList: MutableList<Data>
    lateinit var listview: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        //setSupportActionBar(toolbar)
        employeeList = mutableListOf()
        listview = findViewById(R.id.userData)
        currentUser?.let { user ->
            textView6.text = user.email
            val rootRef = FirebaseDatabase.getInstance().reference
            val userNameRef = rootRef.child("users").orderByChild("email").equalTo(user.email);
            val eventListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        //create new user
                        textView8.text = "Not Found"
                    } else {
                        employeeList.clear()
                        for (e in dataSnapshot.children) {
                            val employee = e.getValue(Data::class.java)
                            employeeList.add(employee!!)
                        }
                        val adapter = customAdapter(this@homeActivity, R.layout.users, employeeList)
                        listview.adapter = adapter
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                }
            }
            userNameRef.addListenerForSingleValueEvent(eventListener)

        }

        val navController = Navigation.findNavController(this, R.id.fragment)
        NavigationUI.setupWithNavController(nav_view, navController)
        NavigationUI.setupActionBarWithNavController(
            this,
            navController, drawer_layout
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            Navigation.findNavController(this, R.id.fragment),
            drawer_layout
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_logout) {

            AlertDialog.Builder(this).apply {
                setTitle("Are you sure?")
                setPositiveButton("Yes") { _, _ ->

                    FirebaseAuth.getInstance().signOut()
                    logout()

                }
                setNegativeButton("Cancel") { _, _ ->
                }
            }.create().show()

        }
        return super.onOptionsItemSelected(item)
    }

}

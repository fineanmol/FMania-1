package com.example.fmania

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_user_login.*

class userLoginActivity : AppCompatActivity() {

    lateinit var email:EditText
    lateinit var pass:EditText
    lateinit var login:Button
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)

        email=findViewById(R.id.u_email)
        pass=findViewById(R.id.u_pass)
        login=findViewById(R.id.u_login)

        login.setOnClickListener {
            saveData()
        }

    }

    private fun saveData(){

        val email=u_email.text.toString().trim()
        val pass=u_pass.text.toString().trim()

        if (email.isEmpty()) {
            u_email.error = "Please enter your email"
            return
        }
        if (pass.isEmpty()) {
            u_pass.error = "Please enter your pass"
            return
        }

        database = FirebaseDatabase.getInstance().reference
        val id=database.push().key
        val user=Data(id.toString(),email,pass)
        database.child("users").child(id.toString()).setValue(user).addOnCompleteListener{
            Toast.makeText(applicationContext,"Completed",Toast.LENGTH_LONG).show()
            val intent = Intent(this@userLoginActivity, getDataActivity::class.java)
            startActivity(intent)
        }


    }
}

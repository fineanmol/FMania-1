package com.example.fmania

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_user_login.*

class userLoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)

        mAuth = FirebaseAuth.getInstance()

        u_login.setOnClickListener {
            val email = u_email.text.toString().trim()
            val password = u_password.text.toString().trim()

            if (email.isEmpty()) {
                u_email.error = "Email Required"
                u_email.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                u_email.error = "Valid Email Required"
                u_email.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty() || password.length < 6) {
                u_password.error = "6 char password required"
                u_password.requestFocus()
                return@setOnClickListener
            }

            loginUser(email, password)
        }

        u_l_register.setOnClickListener {
            startActivity(Intent(this@userLoginActivity, userRegisterActivity::class.java))
        }

        text_view_forget_password.setOnClickListener {
            startActivity(Intent(this@userLoginActivity, userResetPasswordActivity::class.java))
        }
    }

    private fun loginUser(email: String, password: String) {
        progressbar.visibility = View.VISIBLE
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {

                    task ->
                progressbar.visibility = View.GONE
                if (task.isSuccessful) {
                    login()

                } else {
                    task.exception?.message?.let {
                        toast(it)
                    }
                }
            }
    }

    override fun onStart() {
        super.onStart()
        mAuth.currentUser?.let {
            login()

        }
    }
}

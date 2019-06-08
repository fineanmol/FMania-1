package com.example.fmania

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_user_register.*


class userRegisterActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_register)

        mAuth = FirebaseAuth.getInstance()


        u_register.setOnClickListener {
            val email = u_r_email.text.toString().trim()
            val password = u_r_pass.text.toString().trim()

            if (email.isEmpty()) {
                u_r_email.error = "Email Required"
                u_r_email.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                u_r_email.error = "Valid Email Required"
                u_r_email.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty() || password.length < 6) {
                u_r_pass.error = "6 char password required"
                u_r_pass.requestFocus()
                return@setOnClickListener
            }

            registerUser(email, password)

        }

        u_r_login.setOnClickListener {
            startActivity(Intent(this@userRegisterActivity, userLoginActivity::class.java))
        }
    }

    private fun registerUser(email: String, password: String) {
        progressbar.visibility = View.VISIBLE
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
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

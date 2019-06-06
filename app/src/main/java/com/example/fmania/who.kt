package com.example.fmania

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_who.*

class who : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_who)

        ownerLogin.setOnClickListener {
            val ownerLoginIntent= Intent(this,ownerLoginActivity::class.java)
            startActivity(ownerLoginIntent)

        }

        userLogin.setOnClickListener {
            val userLoginIntent= Intent(this,userLoginActivity::class.java)
            startActivity(userLoginIntent)

        }
    }
}

package com.example.fmania

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class customAdapter(val mCtx: Context, val layoutId: Int, val employeeList: List<Data>) :
    ArrayAdapter<Data>(mCtx, layoutId, employeeList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutId, null)

        val email = view.findViewById<TextView>(R.id.email)
        val pass = view.findViewById<TextView>(R.id.pass)

        val updateBtn = view.findViewById<TextView>(R.id.update)
        val deleteBtn = view.findViewById<TextView>(R.id.delete)

        val employee = employeeList[position]

        email.text = employee.email
        pass.text = employee.pass

        updateBtn.setOnClickListener {
            updateInfo(employee)
        }

        deleteBtn.setOnClickListener {
            deleteInfo(employee)
        }

        return view
    }

    private fun updateInfo(employee: Data) {

        val builder = AlertDialog.Builder(mCtx)
        builder.setTitle("Update Info")
        val inflater = LayoutInflater.from(mCtx)
        val view = inflater.inflate(R.layout.user_update, null)

        val email = view.findViewById<EditText>(R.id.editemail)
        val pass = view.findViewById<EditText>(R.id.editpass)
        val name = view.findViewById<EditText>(R.id.nameupdate)
        val number = view.findViewById<EditText>(R.id.numberupdate)
        val dob = view.findViewById<EditText>(R.id.numberupdate)

        email.setText(employee.email)
        pass.setText(employee.pass)

        builder.setView(view)

        builder.setPositiveButton("Update", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {

                val myDatabase = FirebaseDatabase.getInstance().getReference("users")

                val email1 = email.text.toString().trim()
                val pass2 = pass.text.toString().trim()
                val name= name.text.toString().trim()
                val number =number.text.toString().trim()
                val dob = dob.text.toString().trim()

                if (email1.isEmpty()) {
                    email.error = "Please enter your email"
                    return
                }
                if (pass2.isEmpty()) {
                    pass.error = "Please enter your pass"
                    return
                }

                val employee = Data(employee.id, email1, pass2,name,number,dob)
                myDatabase.child(employee.id).setValue(employee)
                Toast.makeText(mCtx, "Updated :) ", Toast.LENGTH_LONG).show()


            }
        })

        builder.setNegativeButton("cancel", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {

            }

        })

        val alert = builder.create()
        alert.show()

    }


    private fun deleteInfo(employee: Data) {
        val myDatabase = FirebaseDatabase.getInstance().getReference("users")
        myDatabase.child(employee.id).removeValue()
        Toast.makeText(mCtx, "Deleted !", Toast.LENGTH_LONG).show()
    }
}


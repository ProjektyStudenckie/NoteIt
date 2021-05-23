package com.sww.noteit.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.sww.noteit.R
import com.sww.noteit.model.DataContainer
import com.sww.noteit.model.DatabaseHttpRequests
import kotlinx.android.synthetic.main.activity_login.*
import java.io.File
import java.io.FileOutputStream


class LoginActivity : AppCompatActivity() {
    var fAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        fAuth = FirebaseAuth.getInstance()

        // hide action bar
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }

        var user = ""

        loginBtn.setOnClickListener {
            val input1 = findViewById<TextInputLayout>(R.id.loginName)
            val name: String = input1.editText!!.text.toString()

            user = name

            val input2 = findViewById<TextInputLayout>(R.id.loginPassword)
            val password: String = input2.editText!!.text.toString()

            val fireREf = FirebaseDatabase.getInstance().getReference("Note It")

            fireREf.setValue("gowno").addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.e(
                        "Suc ! " , task.exception!!.message!!
                    )
                } else {
                    Log.e(
                        "Error ! " , task.exception!!.message!!
                    )
                }
            }

            if ("" != name && "" != password) {


                fAuth!!.signInWithEmailAndPassword(name, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Logged in Successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                        createUserInstantNoteDb(user)
                    } else {
                        Toast.makeText(
                            this,
                            "Error ! " + task.exception!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }


        goToSignUpBtn.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createUserInstantNoteDb(userName: String){

        val file = File(filesDir, "users.txt")

        FileOutputStream(file).use {
            it.write(userName.toByteArray())
        }
    }
}

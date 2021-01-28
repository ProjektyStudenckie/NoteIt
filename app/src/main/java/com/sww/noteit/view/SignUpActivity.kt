package com.sww.noteit.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.google.android.material.textfield.TextInputLayout
import com.sww.noteit.R
import com.sww.noteit.model.DataContainer
import com.sww.noteit.model.DatabaseHttpRequests
import com.sww.noteit.view_model.AuthenticationViewModel
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class SignUpActivity : AppCompatActivity() {

    private lateinit var viewModel: AuthenticationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // hide action bar
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }

        var user = ""

        signUpBtn.setOnClickListener {

            val input1 = findViewById<TextInputLayout>(R.id.signUpName)
            val fullName: String = input1.editText!!.text.toString()

            val input2 = findViewById<TextInputLayout>(R.id.signUpUserName)
            val userName: String = input2.editText!!.text.toString()

            user = userName

            val input3 = findViewById<TextInputLayout>(R.id.signUpEmail)
            val email: String = input3.editText!!.text.toString()

            val input4 = findViewById<TextInputLayout>(R.id.signUpPassword)
            val password: String = input4.editText!!.text.toString()

            if ("" != userName && "" != password && "" != fullName && "" != email) {
                DatabaseHttpRequests.sendPostLoginRequest(userName,password)
            }
        }

        DataContainer.registration.observe(this, { authentication ->
            authentication?.let {
                Log.e("kurczak","kurczak")
                if (authentication) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    Log.e("kurczak","kurczak")

                    createUserInstantNoteDb(user)
                }
                else {
                    Toast.makeText(this, "Username Is Taken", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun createUserInstantNoteDb(userName: String){

        val file = File(filesDir, "users.txt")

        FileOutputStream(file).use {
            it.write(userName.toByteArray())
        }

        val inputAsString = FileInputStream(file).bufferedReader().use { it.readText() }
        Log.e("user: ", inputAsString)
        Log.e("dir: ", filesDir.toString())
    }
}
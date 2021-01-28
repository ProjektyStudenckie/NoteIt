package com.sww.noteit.view

import android.content.Context
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
import kotlinx.android.synthetic.main.activity_login.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var user: String = ""

        loginBtn.setOnClickListener {

            val input1 = findViewById<TextInputLayout>(R.id.loginName)
            val name: String = input1.editText!!.text.toString()

            user = name

            val input2 = findViewById<TextInputLayout>(R.id.loginPassword)
            val password: String = input2.editText!!.text.toString()

            if ("" != name && "" != password) {
                DatabaseHttpRequests.sendGetLoginRequest(name, password)
            }
        }

        DataContainer.authentication.observe(this, { authentication ->
            authentication?.let {
                Log.e("kurczak", "kurczak")
                if (authentication) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    Log.e("kurczak", "kurczak")

                    createUserInstantNoteDb(user)
                } else {
                    Toast.makeText(this, "Wrong user name or password.", Toast.LENGTH_SHORT).show()
                }
            }
        })

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

        val inputAsString = FileInputStream(file).bufferedReader().use { it.readText() }
        Log.e("user: ", inputAsString)
        Log.e("dir: ", filesDir.toString())
    }
}

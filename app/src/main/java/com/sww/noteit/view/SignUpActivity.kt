package com.sww.noteit.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.sww.noteit.R
import com.sww.noteit.model.DataContainer
import com.sww.noteit.view_model.AuthenticationViewModel
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.io.File
import java.io.FileOutputStream
import java.util.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var viewModel: AuthenticationViewModel
    var fAuth: FirebaseAuth? = null
    var fStore: FirebaseFirestore? = null
    var userID: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        fAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
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


                // register the user in firebase
                fAuth!!.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "User Created.", Toast.LENGTH_SHORT)
                                .show()
                            userID = fAuth!!.currentUser!!.uid
                            val documentReference: DocumentReference =
                                fStore!!.collection("users").document(userID!!)
                            val user: MutableMap<String, Any> =
                                HashMap()
                            user["fName"] = fullName
                            user["email"] = email
                            documentReference.set(user)
                                .addOnSuccessListener(OnSuccessListener<Void?> {
                                    Log.d(
                                        "error",
                                        "onSuccess: user Profile is created for $userID"
                                    )
                                }).addOnFailureListener(OnFailureListener { e ->
                                Log.d(
                                    "error",
                                    "onFailure: $e"
                                )
                            })
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            createUserInstantNoteDb(user["email"].toString())
                        } else {
                            Toast.makeText(
                                this,
                                "Error ! " + task.exception!!.message,
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }

            }
        }
    }

    private fun createUserInstantNoteDb(userName: String){

        val file = File(filesDir, "users.txt")

        FileOutputStream(file).use {
            it.write(userName.toByteArray())
        }
    }
}
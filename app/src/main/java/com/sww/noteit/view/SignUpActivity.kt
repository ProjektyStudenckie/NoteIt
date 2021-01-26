package com.sww.noteit.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.sww.noteit.R
import com.sww.noteit.model.DataContainer
import com.sww.noteit.model.DatabaseHttpRequests
import com.sww.noteit.view_model.AuthenticationViewModel
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var viewModel: AuthenticationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signUpBtn.setOnClickListener {
            var userName: String = signUpUserName.text.toString()
            var fullName: String = signUpName.text.toString()
            var password: String = signUpPassword.text.toString()
            var email: String = signUpEmail.text.toString()

            if (!"".equals(userName) && !"".equals(password) && !"".equals(fullName) && !"".equals(email)) {
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
                }
                else {
                    Toast.makeText(this, "Username Is Taken", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
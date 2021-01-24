package com.sww.noteit.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sww.noteit.R
import com.sww.noteit.view_model.AuthenticationViewModel
import kotlinx.android.synthetic.main.activity_login.*
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
//                viewModel.checkIfUserExists(name, password)
//                var userExists: Boolean = viewModel.userExits
                var userExists: Boolean = false

                if (!userExists) {
//                    viewModel.addNewUser()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
            } else {
                Toast.makeText(this, "Invalid values.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
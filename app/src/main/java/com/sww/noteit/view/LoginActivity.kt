package com.sww.noteit.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.sww.noteit.R
import com.sww.noteit.view_model.AuthListener
import com.sww.noteit.view_model.AuthenticationViewModel
import com.sww.noteit.view_model.NoteViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: AuthenticationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginBtn.setOnClickListener {
            var name: String = loginName.text.toString()
            var password: String = loginPassword.text.toString()

            if (!"".equals(name) && !"".equals(password)) {
//                viewModel.checkIfUserExists(name, password)
//                var userExists: Boolean = viewModel.userExits
                var userExists: Boolean = true

                if (userExists) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            } else {
                Toast.makeText(this, "Wrong user name or password.", Toast.LENGTH_SHORT).show()
            }
        }

        goToSignUpBtn.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

}

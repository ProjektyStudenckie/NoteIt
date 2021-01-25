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
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginBtn.setOnClickListener {
            var name: String = loginName.text.toString()
            var password: String = loginPassword.text.toString()

            if (!"".equals(name) && !"".equals(password)) {
                DatabaseHttpRequests.sendGetLoginRequest(name,password)
            }
        }

        DataContainer.authentication.observe(this, { authentication ->
            authentication?.let {
                Log.e("kurczak","kurczak")
                if (authentication) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    Log.e("kurczak","kurczak")
                }
                else {
                    Toast.makeText(this, "Wrong user name or password.", Toast.LENGTH_SHORT).show()
                }
            }
        })

        goToSignUpBtn.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

}

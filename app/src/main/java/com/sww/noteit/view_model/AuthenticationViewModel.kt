package com.sww.noteit.view_model

import android.view.View
import androidx.lifecycle.ViewModel
import com.sww.noteit.view_model.AuthListener

class AuthenticationViewModel : ViewModel() {

    var userExits: Boolean = false

    fun checkIfUserExists(userName: String, password: String) {
//        callback to database - return bool and set userExists value
        userExits = true
    }

    fun addNewUser(userName: String, password: String, email: String, fullName: String){
//        callback to database - add new user
    }
}
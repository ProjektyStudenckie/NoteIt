package com.sww.noteit.view_model

import androidx.lifecycle.ViewModel

class AuthenticationViewModel : ViewModel() {

    var userExits: Boolean = false


    fun addNewUser(userName: String, password: String, email: String, fullName: String){
//        callback to database - add new user
    }
}
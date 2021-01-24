package com.sww.noteit.model

data class User(
    val id: Int = 0,
    var userName: String,
    var email: String,
    var fullName: String,
    var password: String
)
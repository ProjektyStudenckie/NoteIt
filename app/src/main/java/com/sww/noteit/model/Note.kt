package com.sww.noteit.model

import kotlinx.serialization.Serializable


@Serializable
data class Note(
    val ID: Int = 0,
    var UserID:String,
    var Title: String,
    var Note: String,
    var DateDate: String,
    var ImageURL:String
)


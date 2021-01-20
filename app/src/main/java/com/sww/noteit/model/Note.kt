package com.sww.noteit.model


data class Note(
    val id: Int = 0,
    var userName:String,
    var title: String,
    var content: String,
    var date: String,
    var imageUrl:String
)
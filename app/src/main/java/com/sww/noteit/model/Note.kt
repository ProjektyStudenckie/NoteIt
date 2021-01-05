package com.sww.noteit.model

import java.util.*


data class Note(
    val id: Int = 0,
    var title: String,
    var content: String,
    var date: Date = Calendar.getInstance().time
)
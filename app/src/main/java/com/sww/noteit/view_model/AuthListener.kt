package com.sww.noteit.view_model

interface AuthListener {
    fun success(message:String)
    fun failure(message:String)
}
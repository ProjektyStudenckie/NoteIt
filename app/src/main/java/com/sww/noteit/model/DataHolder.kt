package com.sww.noteit.model

import android.widget.EditText
import java.io.File

class DataHolder {
    companion object{
        lateinit var SINGLETON_currentNote: File
        var SINGLETON_noteContent = ""
        var isInitialized = false
        lateinit var editText: EditText
    }
}
package com.sww.noteit.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class NotesViewModel : ViewModel() {


    private var _shouldAddNewNote = MutableLiveData<Boolean>().apply { value = false }
    val shouldAddNewNote: LiveData<Boolean>
        get() = _shouldAddNewNote


    fun addNewNote() {
        _shouldAddNewNote.value = true
    }

    fun addNewNoteDone() {
        _shouldAddNewNote.value = false
    }
}
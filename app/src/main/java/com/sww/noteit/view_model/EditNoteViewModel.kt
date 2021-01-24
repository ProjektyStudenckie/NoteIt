package com.sww.noteit.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class EditNoteViewModel(noteID: Int?, application: Application) : AndroidViewModel(application) {



    val title = MutableLiveData<String>()

    val showInvalidMessage = MutableLiveData<Boolean>().apply { value = false }

    private var _finishActivity = MutableLiveData<Boolean>().apply { value = false }
    val finishActivity: LiveData<Boolean>
        get() = _finishActivity

    private var _deleteNote = MutableLiveData<Boolean>().apply { value = false }
    val deleteNote: LiveData<Boolean>
        get() = _deleteNote

    fun deleteNoteFromDB() {
        //TODO: Delete note from db

        _deleteNote.value = true
    }

    fun saveNote() {
        if (!validate()) {
            showInvalidMessage.value = true
            return
        }

        //TODO: Save note in db
        //DatabaseHttpRequests.sendUpdateNotesRequest()
        _finishActivity.value = true
    }

    private fun validate(): Boolean {
        return !title.value.isNullOrEmpty()
    }
}

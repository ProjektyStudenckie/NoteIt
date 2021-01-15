package com.sww.noteit.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sww.noteit.model.DatabaseHttpRequests


class EditNoteViewModel(noteID: Int?, application: Application) : AndroidViewModel(application) {



    val title = MutableLiveData<String>()

    val showInvalidMessage = MutableLiveData<Boolean>().apply { value = false }

    private var _finishActivity = MutableLiveData<Boolean>().apply { value = false }
    val finishActivity: LiveData<Boolean>
        get() = _finishActivity

    private var _deleteNote = MutableLiveData<Boolean>().apply { value = false }
    val deleteNote: LiveData<Boolean>
        get() = _deleteNote

    val request = DatabaseHttpRequests.Companion.sendGetLoginRequest("wielok","Wielocukier98")
    val request2 = DatabaseHttpRequests.Companion.sendGetNotesRequest("wielok")
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

        _finishActivity.value = true
    }

    private fun validate(): Boolean {
        return !title.value.isNullOrEmpty()
    }
}

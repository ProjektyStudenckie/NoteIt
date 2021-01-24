package com.sww.noteit.model

import androidx.lifecycle.MutableLiveData
import okhttp3.ResponseBody


class DataContainer {

    companion object {
        var userName:String="Wielok"
        lateinit var response:ResponseBody
        var allNotes:MutableLiveData<List<Note>> = MutableLiveData<List<Note>>()

        fun Refresh(){
            DatabaseHttpRequests.sendGetNotesRequest(userName)
        }

        //fun GetAllNotesFromDatabase():MutableList<Note>{

        //    DatabaseHttpRequests.sendGetNotesRequest(userName)
        //}
    }
}
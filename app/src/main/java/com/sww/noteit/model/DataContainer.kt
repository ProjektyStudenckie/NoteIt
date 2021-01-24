package com.sww.noteit.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import okhttp3.ResponseBody
import java.time.format.DateTimeFormatter
import java.util.*


class DataContainer {

    companion object {
        var userName:String="Wielok"
        lateinit var response:ResponseBody
        var allNotes:MutableLiveData<List<Note>> = MutableLiveData<List<Note>>()

        @RequiresApi(Build.VERSION_CODES.O)
        val format = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.GERMAN)


        fun Refresh(){
            DatabaseHttpRequests.sendGetNotesRequest(userName)
        }
        fun GetBiggestId():Int{
            var value=0
            allNotes.value?.forEach {

                if(it.ID>value){
                    value =it.ID
                }
            }
            return value
        }

        //fun GetAllNotesFromDatabase():MutableList<Note>{

        //    DatabaseHttpRequests.sendGetNotesRequest(userName)
        //}
    }
}
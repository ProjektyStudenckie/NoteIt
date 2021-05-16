package com.sww.noteit.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import okhttp3.ResponseBody
import java.time.format.DateTimeFormatter
import java.util.*


class DataContainer {

    companion object {

        private lateinit var databaseReference : DatabaseReference
        private lateinit var listOfNotes : ArrayList<DataBaseRow>

        var authentication: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
        var registration: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
        var allNotes:MutableLiveData<List<Note>> = MutableLiveData<List<Note>>()



        var userName:String=String()
        lateinit var currentNote:Note
        lateinit var response:ResponseBody

        @RequiresApi(Build.VERSION_CODES.O)
        val format = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.GERMAN)


        fun Refresh(){
            DatabaseHttpRequests.sendGetNotesRequest(userName)
            val fireBase = FirebaseDatabase.getInstance()
            databaseReference = fireBase.getReference("NoteIt")
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
package com.sww.noteit.model

import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseError
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.sww.noteit.view.MainActivity
import okhttp3.*
import java.util.HashMap


class DatabaseHttpRequests {

    companion object {
        fun sendPostNotesRequest(note:Note) {
            val fireREf =Firebase.database.getReference("Note It")


            fireREf.keepSynced(true)

            fireREf.child("siemson").push().setValue("gowno").addOnFailureListener {
                Log.e("TAG", it.toString())
            }

        }

        fun sendUpdateNotesRequest(note:Note) {

        }

        fun sendGetNotesRequest(userName: String) {

            val fireREf = FirebaseDatabase.getInstance().getReference().child("Note")
            val postListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val post = dataSnapshot.getValue()
                    val gson = Gson()
                    Log.e("TAG", gson.toJson(post))

                    //al obj =Json.decodeFromString<List<Note>>(gson.toJson(post))
                    //allNotes.postValue(obj)
                    // ...
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("rr", "loadPost:onCancelled", databaseError.toException())
                }
            }
            fireREf.addValueEventListener(postListener)

            }


        fun sendDeleteNotesRequest(userName: String,iD : Int) {
        }
    }
}

package com.sww.noteit.model
import android.util.Log
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.sww.noteit.model.DataContainer.Companion.allNotes


class DatabaseHttpRequests {

    companion object {
        fun sendPostNotesRequest(note:Note) {
            val fireREf =Firebase.database.getReference("Note It")
            fireREf.push().setValue(note)
        }

        fun sendUpdateNotesRequest(note:Note) {
            val fireREf = FirebaseDatabase.getInstance().getReference("Note It")

            fireREf.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var allNote = mutableListOf<Note>()
                    dataSnapshot.children.forEach {
                        if (it.getValue<Note>()!!.ID == note.ID) {
                            it.ref.setValue(note)
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("The read failed: " + databaseError.code)
                }
            })
        }

        fun sendGetNotesRequest(userName: String) {

            val fireREf = FirebaseDatabase.getInstance().getReference("Note It")


            fireREf.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var allNote = mutableListOf<Note>()
                    dataSnapshot.children.forEach{
                        allNote.add(it.getValue<Note>()!!)
                        allNotes.postValue(allNote)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("The read failed: " + databaseError.code)
                }
            })


            }


        fun sendDeleteNotesRequest(userName: String,iD : Int) {
            val fireREf = FirebaseDatabase.getInstance().getReference("Note It")

            fireREf.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var allNote = mutableListOf<Note>()
                    dataSnapshot.children.forEach {
                        if (it.getValue<Note>()!!.ID == iD) {
                            it.ref.removeValue()
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("The read failed: " + databaseError.code)
                }
            })


        }

}}

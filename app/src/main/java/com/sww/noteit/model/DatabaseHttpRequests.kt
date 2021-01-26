package com.sww.noteit.model

import android.util.Log
import com.sww.noteit.model.DataContainer.Companion.allNotes
import com.sww.noteit.model.DataContainer.Companion.authentication
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class DatabaseHttpRequests {

    companion object {
        fun sendPostLoginRequest(userName: String, password: String) {

            val url = "https://gwldrwcys5.execute-api.us-east-1.amazonaws.com/Prod/login/register?UserID=$userName&Password=$password"


            val payload = "test payload"
            DataContainer.userName=userName

            val okHttpClient = OkHttpClient()
            val requestBody = payload.toRequestBody()
            val request = Request.Builder()
                .method("POST", requestBody)
                .url(url)
                .build()
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // Handle this
                }

                override fun onResponse(call: Call, response: Response) {
                    DataContainer.registration.postValue(response.body?.string().toBoolean())

                }
            })

        }

        fun sendPostNotesRequest(note:Note) {
            val userName=note.UserID
            val UUID=note.ID
            val imageUrl=note.ImageURL
            val title=note.Title
            val content=note.Note
            val date=note.DateDate

            Log.e("values",userName+UUID+imageUrl+title+content+date)
            val url = "https://gwldrwcys5.execute-api.us-east-1.amazonaws.com/Prod/post-note-for-user?UserID=$userName&UUID=$UUID&ImageUrl=$imageUrl&Title=$title&Note=$content&Date=$date"
            val payload = "test payload"

            val okHttpClient = OkHttpClient()
            val requestBody = payload.toRequestBody()
            val request = Request.Builder()
                .method("POST", requestBody)
                .url(url)
                .build()
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("response", "failure")
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.e("response", response.body?.string() as String)
                    DataContainer.Refresh()
                }
            })

        }

        fun sendUpdateNotesRequest(note:Note) {
            val userName=note.UserID
            val UUID=note.ID
            val imageUrl=note.ImageURL
            val title=note.Title
            val content=note.Note
            val date=note.DateDate

            val url = "https://gwldrwcys5.execute-api.us-east-1.amazonaws.com/Prod/updatenote?UserID=$userName&UUID=$UUID&ImageUrl=$imageUrl&Title=$title&Note=$content&Date=$date"
            val payload = "test payload"

            val okHttpClient = OkHttpClient()
            val requestBody = payload.toRequestBody()
            val request = Request.Builder()
                .method("POST", requestBody)
                .url(url)
                .build()
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // Handle this
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.e("response", response.body?.string() as String)
                    DataContainer.Refresh()
                }
            })

        }

        fun sendGetLoginRequest(userName: String, password: String) {
            val url ="https://gwldrwcys5.execute-api.us-east-1.amazonaws.com/Prod/login/login?UserID=$userName&Password=$password"

            val request=Request.Builder().url(url).build()

            DataContainer.userName=userName
            val client = OkHttpClient()
            client.newCall(request).enqueue(object :Callback{
                override fun onResponse(call: Call, response: Response) {
                    authentication.postValue(response.body?.string().toBoolean())
                    DataContainer.Refresh()
                }
                override fun onFailure(call: Call, e: IOException) {
                    TODO("Not yet implemented")
                }
            })
        }

        fun sendGetNotesRequest(userName: String) {
            val url ="https://gwldrwcys5.execute-api.us-east-1.amazonaws.com/Prod/get-notes-from-user?UserID=$userName"

            val request=Request.Builder().url(url).build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object :Callback{
                override fun onResponse(call: Call, response: Response) {
                    if(response.body!=null){
                        DataContainer.response = response.body as ResponseBody
                        val obj =Json.decodeFromString<List<Note>>(response.body?.string() as String)
                        allNotes.postValue(obj)
                    }
                }
                override fun onFailure(call: Call, e: IOException) {



                }
            })
        }
        
        fun sendDeleteNotesRequest(userName: String,iD : Int) {
            Log.e(userName,iD.toString())
            val url ="https://gwldrwcys5.execute-api.us-east-1.amazonaws.com/Prod/deletenote?UserID=$userName&UUID=$iD"

            val request=Request.Builder().url(url).build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object :Callback{
                override fun onResponse(call: Call, response: Response) {
                    Log.e("response", response.body?.string() as String)
                    DataContainer.Refresh()
                }
                override fun onFailure(call: Call, e: IOException) {
                    TODO("Not yet implemented")
                }
            })
        }
    }
}
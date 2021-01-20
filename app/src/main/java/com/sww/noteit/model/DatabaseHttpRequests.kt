package com.sww.noteit.model

import android.util.Log
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class DatabaseHttpRequests {

    companion object {
        fun sendPostLoginRequest(userName: String, password: String) {

            val url = "https://gwldrwcys5.execute-api.us-east-1.amazonaws.com/Prod/login/register?UserID=$userName&Password=$password"


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
                }
            })

        }

        fun sendPostNotesRequest(note:Note) {
            val userName=note.userName
            val UUID=note.id
            val imageUrl=note.imageUrl
            val title=note.title
            val content=note.content
            val date=note.date

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
                }
            })

        }

        fun sendUpdateNotesRequest(note:Note) {
            val userName=note.userName
            val UUID=note.id
            val imageUrl=note.imageUrl
            val title=note.title
            val content=note.content
            val date=note.date

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
                }
            })

        }

        fun sendGetLoginRequest(userName: String, password: String) {
            val url ="https://gwldrwcys5.execute-api.us-east-1.amazonaws.com/Prod/login/login?UserID=$userName&Password=$password"

            val request=Request.Builder().url(url).build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object :Callback{
                override fun onResponse(call: Call, response: Response) {
                    Log.e("response", response.body?.string() as String)
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
                    Log.e("response", response.body?.string() as String)
                }
                override fun onFailure(call: Call, e: IOException) {
                    TODO("Not yet implemented")
                }
            })
        }
        
        fun sendDeleteNotesRequest(userName: String,iD : Int) {
            val url ="https://gwldrwcys5.execute-api.us-east-1.amazonaws.com/Prod/deletenote?UserID=$userName&UUID=$iD"

            val request=Request.Builder().url(url).build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object :Callback{
                override fun onResponse(call: Call, response: Response) {
                    Log.e("response", response.body?.string() as String)
                }
                override fun onFailure(call: Call, e: IOException) {
                    TODO("Not yet implemented")
                }
            })
        }
    }
}
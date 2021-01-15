package com.sww.noteit.model

import android.util.Log
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class DatabaseHttpRequests {

    companion object {
        fun sendPostRequest(userName: String, password: String) {

            val url ="https://gwldrwcys5.execute-api.us-east-1.amazonaws.com/Prod/login/login?UserID=$userName&Password=$password"

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
    }
}
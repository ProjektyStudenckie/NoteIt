package com.sww.noteit.model

import android.util.Log
import okhttp3.*
import java.io.IOException

class DatabaseHttpRequests {

    companion object {
        fun sendPostRequest(userName: String, password: String) {

           
        }

        fun sendGetLoginRequest(userName: String, password: String) {
            val url ="https://gwldrwcys5.execute-api.us-east-1.amazonaws.com/Prod/login/login?UserID=$userName&Password=$password"

            val request=Request.Builder().url(url).build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object :Callback{
                override fun onResponse(call: Call, response: Response) {
                    Log.e("response", response.body()?.string() as String)
                }
                override fun onFailure(call: Call, e: IOException) {
                    TODO("Not yet implemented")
                }
            })
            Log.e("response", request.body().toString())
        }

        fun sendGetNotesRequest(userName: String) {
            val url ="https://gwldrwcys5.execute-api.us-east-1.amazonaws.com/Prod/get-notes-from-user?UserID=$userName"

            val request=Request.Builder().url(url).build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object :Callback{
                override fun onResponse(call: Call, response: Response) {
                    Log.e("response", response.body()?.string() as String)
                }
                override fun onFailure(call: Call, e: IOException) {
                    TODO("Not yet implemented")
                }
            })
            Log.e("response", request.body().toString())
        }
    }
}
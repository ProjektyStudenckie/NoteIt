package com.sww.noteit.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sww.noteit.R
import com.sww.noteit.model.DatabaseHttpRequests
import com.sww.noteit.model.Note

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_instant_note,
                R.id.navigation_notes,
                R.id.navigation_settings
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        DatabaseHttpRequests.sendPostNotesRequest(Note(35,"Wielok","Kurczak","Ziemniak chodził po polanie i sie smazyl","dzis","wykop.pl"))
        DatabaseHttpRequests.sendUpdateNotesRequest(Note(34,"Wielok","Ziemniak","Ziemniak chodził po polanie i sie smazyl i skakal","dzis","wykop./mikroblog"))
        Log.e("response", "leci")
    }
}
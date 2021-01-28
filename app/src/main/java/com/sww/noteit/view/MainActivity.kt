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
import com.sww.noteit.model.DataContainer
import com.sww.noteit.model.DataHolder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        InstantNoteFragment.NoteSingleton.loadInstantNoteContent()
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_instant_note,
                R.id.navigation_notes,
                R.id.navigation_settings
            )
        )

        DataContainer.Refresh()

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

//        navController.addOnDestinationChangedListener { controller, destination, arguments ->
//            if (destination.id == R.id.navigation_instant_note){
//                Log.e("dest", "instant note")
//
//                if (DataHolder.isInitialized){
//                    Log.e("content", "is init")
//                    InstantNoteFragment.NoteSingleton.loadInstantNoteContent()
//                    Log.e("sd",DataHolder.SINGLETON_noteContent)
//                }
//            }
//        }
    }
}
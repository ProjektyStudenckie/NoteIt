package com.sww.noteit.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sww.noteit.R
import com.sww.noteit.databinding.ActivityNoteBinding
import com.sww.noteit.view_model.NoteViewModel
import com.sww.noteit.view_model.NoteViewModelFactory
import kotlinx.android.synthetic.main.activity_note.*

class NoteActivity : AppCompatActivity() {

    private lateinit var noteViewModel: NoteViewModel
    private var noteID: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityNoteBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_note)

        noteID = intent.extras?.getInt(NotesFragment.NOTE_ID)

        setSupportActionBar(toolbar_note)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // TODO: get title of the note and set supportActionBar title with it
        supportActionBar?.title = "Note"

        val noteViewModelFactory = NoteViewModelFactory(noteID, application)
        noteViewModel =
            ViewModelProvider(this, noteViewModelFactory).get(NoteViewModel::class.java)
        binding.lifecycleOwner = this
        binding.noteViewModel = noteViewModel

    }

    override fun onSupportNavigateUp(): Boolean {
        //TODO: Update note in db
        Toast.makeText(this, "Note Saved 💾", Toast.LENGTH_SHORT).show()

        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_edit_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.edit_note_menu_item) {
            val intent = Intent(this, EditNoteActivity::class.java)
            intent.putExtra(NotesFragment.NOTE_ID, noteID)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
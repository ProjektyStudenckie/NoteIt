package com.sww.noteit.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.sww.noteit.R
import com.sww.noteit.databinding.ActivityEditNoteBinding
import com.sww.noteit.model.DataContainer
import com.sww.noteit.model.DatabaseHttpRequests
import com.sww.noteit.view_model.EditNoteViewModel
import com.sww.noteit.view_model.EditNoteViewModelFactory
import kotlinx.android.synthetic.main.activity_edit_note.*


class EditNoteActivity : AppCompatActivity() {

    private lateinit var editNoteViewModel: EditNoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityEditNoteBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_edit_note)

        val noteID = intent.extras?.getInt(NotesFragment.NOTE_ID)

        setSupportActionBar(toolbar_edit_note)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = DataContainer.currentNote.Title

        val editNoteViewModelFactory = EditNoteViewModelFactory(noteID, application)
        editNoteViewModel =
            ViewModelProvider(this, editNoteViewModelFactory).get(EditNoteViewModel::class.java)
        binding.lifecycleOwner = this
        binding.editNoteViewModel = editNoteViewModel

        til_note_title.editText?.setText(DataContainer.currentNote.Title)

        editNoteViewModel.finishActivity.observe(this) {
            if (it) {
                DataContainer.currentNote.Title=til_note_title.editText?.getText().toString()
                DatabaseHttpRequests.sendUpdateNotesRequest(DataContainer.currentNote)
                finish()
            }
        }

        editNoteViewModel.deleteNote.observe(this) {
            if (it) {
                DatabaseHttpRequests.sendDeleteNotesRequest(DataContainer.userName, DataContainer.currentNote.ID)
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }
        }
    }
}
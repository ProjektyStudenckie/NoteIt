package com.sww.noteit.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sww.noteit.R
import com.sww.noteit.databinding.InstantNoteFragmentBinding
import com.sww.noteit.model.DataHolder
import com.sww.noteit.view_model.InstantNoteViewModel
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class InstantNoteFragment : Fragment() {

    private lateinit var instantNoteViewModel: InstantNoteViewModel

    lateinit var currentNote: File
    var shouldLoad = false

    private lateinit var et_noteContent: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)

        val binding: InstantNoteFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.instant_note_fragment, container, false)

        instantNoteViewModel = ViewModelProvider(this).get(InstantNoteViewModel::class.java)

        binding.lifecycleOwner = this
        binding.instantNoteViewModel = instantNoteViewModel

        et_noteContent = binding.root.findViewById(R.id.et_note_content)
        DataHolder.editText = et_noteContent

        // detect change and save them to db, might also save after every activity change
        et_noteContent.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val data: String = "" + s
                Log.e("note: ", data)
                saveDataInternally(data)
                shouldLoad = true
            }
        })

        return binding.root
    }

    object NoteSingleton {
        fun loadInstantNoteContent() {
            val note = DataHolder.SINGLETON_currentNote

            Log.e("note content: ", DataHolder.SINGLETON_noteContent)

            // everythin ok TODO update edit text
            DataHolder.editText.setText(DataHolder.SINGLETON_noteContent)

            val inputAsString = FileInputStream(note).bufferedReader().use { it.readText() }
            Log.e("after: ", inputAsString)

            Log.e("edit text: ", DataHolder.editText.text.toString())
        }
    }

    fun saveDataInternally(noteContent: String){
        val dir = "/data/user/0/com.sww.noteit/files"
        val file = File(dir, "users.txt")

        // get currrent user name from users.txt file
        val currentUser = FileInputStream(file).bufferedReader().use { it.readText() }

        // create new directory for instantNotes
        val instatNoteDbDir = File(dir, "instantNoteDB")
        instatNoteDbDir.mkdirs()

        // create new file for currrent user
        val note = File(instatNoteDbDir, "$currentUser.txt")
        currentNote = note
        DataHolder.SINGLETON_currentNote = note
        DataHolder.SINGLETON_noteContent = noteContent
        DataHolder.isInitialized = true

        // write to file (save instant note data)
        FileOutputStream(note).use {
            it.write(noteContent.toByteArray())
        }

        //test
        val inputAsString = FileInputStream(note).bufferedReader().use { it.readText() }
        Log.e("content: ", inputAsString)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.save_note_instant_menu_item) {
            Toast.makeText(this.requireContext(), "Save Instant Note", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_instant_note_menu, menu)
    }
}
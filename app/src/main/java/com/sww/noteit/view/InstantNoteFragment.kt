package com.sww.noteit.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sww.noteit.R
import com.sww.noteit.databinding.InstantNoteFragmentBinding
import com.sww.noteit.model.DataContainer
import com.sww.noteit.model.DataHolder
import com.sww.noteit.model.DatabaseHttpRequests
import com.sww.noteit.model.Note
import com.sww.noteit.view_model.InstantNoteViewModel
import com.sww.noteit.view_model.NotesViewModel
import com.sww.noteit.view_model.adapters.NotesListAdapter
import kotlinx.android.synthetic.main.custom_input_dialog.view.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.time.LocalDate


class InstantNoteFragment : Fragment() {

    private lateinit var instantNoteViewModel: InstantNoteViewModel

    private lateinit var notesViewModel: NotesViewModel

//    lateinit var currentNote: File

    private lateinit var et_noteContent: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)

        val binding: InstantNoteFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.instant_note_fragment, container, false)

        notesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        instantNoteViewModel = ViewModelProvider(this).get(InstantNoteViewModel::class.java)

        binding.lifecycleOwner = this
        binding.instantNoteViewModel = instantNoteViewModel

        et_noteContent = binding.root.findViewById(R.id.et_note_content)
        et_noteContent.setText(DataHolder.SINGLETON_noteContent);
        DataHolder.editText = et_noteContent

        // detect change and save them to db, might also save after every activity change
        et_noteContent.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val data: String = "" + s
                saveDataInternally(data)
            }
        })

        return binding.root
    }

    object NoteSingleton {
        fun loadInstantNoteContent() {
            val dir = "/data/user/0/com.sww.noteit/files"

            // create new directory for instantNotes
            val instatNoteDbDir = File(dir, "instantNoteDB")
            instatNoteDbDir.mkdirs()

            // create new file for currrent user
            val note = File(instatNoteDbDir, "${DataContainer.userName}.txt")
            if(note.exists()) {
                DataHolder.SINGLETON_currentNote = note
                DataHolder.SINGLETON_noteContent = note.readText()
                DataHolder.isInitialized = true
            }

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
//        currentNote = note
        DataHolder.SINGLETON_currentNote = note
        DataHolder.SINGLETON_noteContent = noteContent
        DataHolder.isInitialized = true

        // write to file (save instant note data)
        FileOutputStream(note).use {
            it.write(noteContent.toByteArray())
        }

        //test
        val inputAsString = FileInputStream(note).bufferedReader().use { it.readText() }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.save_note_instant_menu_item) {
            Toast.makeText(this.requireContext(), "Save Instant Note", Toast.LENGTH_SHORT).show()

//            val intent = Intent(activity, EditNoteActivity::class.java)
//            startActivity(intent)

            showCreateNewNoteDialog()
            notesViewModel.addNewNoteDone()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_instant_note_menu, menu)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showCreateNewNoteDialog() {
        val builder = AlertDialog.Builder(this.requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.custom_input_dialog, null)

        with(builder) {
            setTitle("Enter The Title")
            setPositiveButton(R.string.confirm) { _, _ ->
                // TODO: Create new note in db and fetch them
                DatabaseHttpRequests.sendPostNotesRequest(
                    Note(
                        DataContainer.GetBiggestId()+1,
                        DataContainer.userName,
                        dialogLayout.et_title.text.toString(),
                        DataHolder.SINGLETON_noteContent,
                        LocalDate.now().format(DataContainer.format),"")
                )
            }
            setNegativeButton(R.string.cancel) { _, _ ->
            }
            setView(dialogLayout)
            show()
        }
    }
}
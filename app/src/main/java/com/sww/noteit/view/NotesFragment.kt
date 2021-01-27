package com.sww.noteit.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sww.noteit.R
import com.sww.noteit.databinding.NotesFragmentBinding
import com.sww.noteit.model.DataContainer
import com.sww.noteit.model.DatabaseHttpRequests
import com.sww.noteit.model.Note
import com.sww.noteit.util.SwipeToDeleteCallback
import com.sww.noteit.view_model.NotesViewModel
import com.sww.noteit.view_model.adapters.NotesListAdapter
import kotlinx.android.synthetic.main.activity_note.view.*
import kotlinx.android.synthetic.main.custom_input_dialog.view.*
import kotlinx.android.synthetic.main.list_item_note.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class NotesFragment : Fragment() {

    companion object {
        const val NOTE_ID = "NOTE_ID"
    }

    private lateinit var notesViewModel: NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: NotesFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.notes_fragment, container, false)

        notesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        binding.lifecycleOwner = this
        binding.notesViewModel = notesViewModel

        val notesListAdapter = NotesListAdapter(this.requireContext())
        binding.notesList.adapter = notesListAdapter


        val swipeHandler = object : SwipeToDeleteCallback(this.requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                requestDeleteConfirmation(notesListAdapter, viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.notesList)

        DataContainer.allNotes.observe(viewLifecycleOwner, { notes ->
            notes?.let {
                notesListAdapter.setNotes(it.toMutableList())
            }
        })


        notesViewModel.shouldAddNewNote.observe(viewLifecycleOwner) {
            if(it) {
                showCreateNewNoteDialog(notesListAdapter)
                notesViewModel.addNewNoteDone()
            }
        }


        notesListAdapter.setOnClickListener(object : NotesListAdapter.OnClickListener {
            override fun onClick(position: Int, model: Note) {
                val intent = Intent(context, NoteActivity::class.java)
                DataContainer.currentNote = model
                intent.putExtra(NOTE_ID, model.ID)
                startActivity(intent)
            }
        })


        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun requestDeleteConfirmation(adapter: NotesListAdapter, adapterPosition: Int) {
        val format = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.GERMAN)


        MaterialAlertDialogBuilder(this.requireContext())
            .setTitle(resources.getString(R.string.delete_note_confirmation))
            .setMessage("${adapter.getNotes()[adapterPosition].Title} [${LocalDate.parse(adapter.getNotes()[adapterPosition].DateDate,format)}]")
            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
                // notifyDataSetChanged to reload items (red background will disappear from selected item)
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.delete)) { dialog, _ ->
                DatabaseHttpRequests.sendDeleteNotesRequest(DataContainer.userName,adapter.getNotes()[adapterPosition].ID)
                dialog.dismiss()
            }
            .show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showCreateNewNoteDialog(notesListAdapter: NotesListAdapter) {
        val builder = AlertDialog.Builder(this.requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.custom_input_dialog, null)

        with(builder) {
            setTitle("Enter The Title")
            setPositiveButton(R.string.confirm) { _, _ ->
                // TODO: Create new note in db and fetch them
                DatabaseHttpRequests.sendPostNotesRequest(Note(DataContainer.GetBiggestId()+1,DataContainer.userName,dialogLayout.et_title.text.toString(),"",LocalDate.now().format(DataContainer.format),""))
                //.setNotes()

            }
            setNegativeButton(R.string.cancel) { _, _ ->
            }
            setView(dialogLayout)
            show()
        }
    }
}
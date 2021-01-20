package com.sww.noteit.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sww.noteit.R
import com.sww.noteit.databinding.NotesFragmentBinding
import com.sww.noteit.model.Note
import com.sww.noteit.util.SwipeToDeleteCallback
import com.sww.noteit.view_model.NotesViewModel
import com.sww.noteit.view_model.adapters.NotesListAdapter
import java.text.SimpleDateFormat
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


        // Removing notes by swiping
        val swipeHandler = object : SwipeToDeleteCallback(this.requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // TODO: Remove note from db
                requestDeleteConfirmation(notesListAdapter, viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.notesList)

        // temporary way to add data to the list


        // TODO: Create LiveData allNotes and fetch the data from db
//        notesViewModel.allNotes.observe(viewLifecycleOwner, { notes ->
//            notes?.let { notesListAdapter.setNotes(it.toMutableList()) }
//        })


        notesViewModel.shouldAddNewNote.observe(viewLifecycleOwner, {
            if(it) {
                showCreateNewNoteDialog(notesListAdapter)
                notesViewModel.addNewNoteDone()
            }
        })


        notesListAdapter.setOnClickListener(object : NotesListAdapter.OnClickListener {
            override fun onClick(position: Int, model: Note) {
                val intent = Intent(context, NoteActivity::class.java)
                intent.putExtra(NOTE_ID, model.id)
                startActivity(intent)
            }
        })


        return binding.root
    }

    private fun requestDeleteConfirmation(adapter: NotesListAdapter, adapterPosition: Int) {
        val format = SimpleDateFormat("dd-MM-yyy", Locale.GERMAN)

        MaterialAlertDialogBuilder(this.requireContext())
            .setTitle(resources.getString(R.string.delete_note_confirmation))
            .setMessage("${adapter.getNotes()[adapterPosition].title} [${format.format(adapter.getNotes()[adapterPosition].date)}]")
            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
                // notifyDataSetChanged to reload items (red background will disappear from selected item)
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.delete)) { dialog, _ ->
                adapter.removeAt(adapterPosition)
                dialog.dismiss()
            }
            .show()
    }

    private fun showCreateNewNoteDialog(notesListAdapter: NotesListAdapter) {
        val builder = AlertDialog.Builder(this.requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.custom_input_dialog, null)

        with(builder) {
            setTitle("Enter The Title")
            setPositiveButton(R.string.confirm) { _, _ ->
                // TODO: Create new note in db and fetch them

                notesListAdapter.notifyDataSetChanged()
            }
            setNegativeButton(R.string.cancel) { _, _ ->
            }
            setView(dialogLayout)
            show()
        }
    }
}
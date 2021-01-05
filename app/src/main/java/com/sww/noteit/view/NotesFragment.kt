package com.sww.noteit.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
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
import kotlinx.android.synthetic.main.list_item_note.view.*
import java.text.SimpleDateFormat
import java.util.*

class NotesFragment : Fragment() {

    private lateinit var notesViewModel: NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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

        // temporary way to create list
        val tempListOfNotes = mutableListOf(
            Note(
                1,
                "Notatka numer 1",
                "dsvkomdsvodnsvoidnsovidsnovisdonvo o sndvodnvods\r osnvodsnvoisnvod"
            ),
            Note(
                2,
                "Notatka numer 2",
                "dsvkomdsvodnsvoidnsovidsnovisdonvo o sndvodnvods\r osnvodsnvoisnvod"
            ),
            Note(
                3,
                "Notatka numer 3",
                "dsvkomdsvodnsvoidnsovidsnovisdonvo o sndvodnvods\r osnvodsnvoisnvod"
            ),
            Note(
                4,
                "Notatka numer 4",
                "dsvkomdsvodnsvoidnsovidsnovisdonvo o sndvodnvods\r osnvodsnvoisnvod"
            )
        )
        notesListAdapter.setNotes(tempListOfNotes)


        // TODO: Create LiveData allNotes and fetch the data from db
//        notesViewModel.allNotes.observe(viewLifecycleOwner, { notes ->
//            notes?.let { notesListAdapter.setNotes(it.toMutableList()) }
//        })


        notesListAdapter.setOnClickListener(object : NotesListAdapter.OnClickListener {
            override fun onClick(position: Int, model: Note) {
                // TODO: Go to NoteActivity
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
}
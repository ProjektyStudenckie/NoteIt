package com.sww.noteit.view

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sww.noteit.R
import com.sww.noteit.databinding.InstantNoteFragmentBinding
import com.sww.noteit.view_model.InstantNoteViewModel


class InstantNoteFragment : Fragment() {

    private lateinit var instantNoteViewModel: InstantNoteViewModel

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

        return binding.root
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
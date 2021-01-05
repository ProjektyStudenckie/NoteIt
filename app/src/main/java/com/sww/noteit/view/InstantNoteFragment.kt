package com.sww.noteit.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sww.noteit.R
import com.sww.noteit.databinding.InstantNoteFragmentBinding
import com.sww.noteit.databinding.NotesFragmentBinding
import com.sww.noteit.view_model.InstantNoteViewModel
import com.sww.noteit.view_model.NotesViewModel


class InstantNoteFragment : Fragment() {

    private lateinit var instantNoteViewModel: InstantNoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: InstantNoteFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.instant_note_fragment, container, false)

        instantNoteViewModel = ViewModelProvider(this).get(InstantNoteViewModel::class.java)

        binding.lifecycleOwner = this
        binding.instantNoteViewModel = instantNoteViewModel

        return binding.root
    }
}
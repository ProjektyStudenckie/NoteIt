package com.sww.noteit.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sww.noteit.R
import com.sww.noteit.databinding.NotesFragmentBinding
import com.sww.noteit.view_model.NotesViewModel

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

        return binding.root
    }
}
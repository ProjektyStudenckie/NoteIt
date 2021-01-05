package com.sww.noteit.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sww.noteit.R
import com.sww.noteit.databinding.SettingsFragmentBinding
import com.sww.noteit.view_model.SettingsViewModel

class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: SettingsFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.settings_fragment, container, false)

        settingsViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        binding.lifecycleOwner = this
        binding.settingsViewModel = settingsViewModel

        return binding.root
    }
}
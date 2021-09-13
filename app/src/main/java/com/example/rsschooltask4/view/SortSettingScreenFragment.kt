package com.example.rsschooltask4.view

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.rsschooltask4.R

class SortSettingScreenFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        return
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.fragment_sort_setting_screen)
    }
}
package com.example.rsschooltask4

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

class SortSettingScreenFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        return
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.fragment_sort_setting_screen)
    }
}
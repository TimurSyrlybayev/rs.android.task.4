package com.example.rsschooltask4.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.DialogFragment
import com.example.rsschooltask4.R

class ExitDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.exit_dialog_question))
            .setPositiveButton(getString(R.string.positive_answer)) { _, _ ->
                finishAffinity(requireActivity()) }
            .setNegativeButton(getString(R.string.negative_answer)) { _, _ ->
                dismiss() }
            .create()
    }

    companion object {
        const val TAG = "ExitDialog"
    }
}
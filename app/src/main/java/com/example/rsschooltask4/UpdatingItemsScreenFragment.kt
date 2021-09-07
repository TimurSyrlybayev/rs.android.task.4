package com.example.rsschooltask4

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.rsschooltask4.databinding.FragmentUpdatingItemsScreenBinding
import com.google.android.material.snackbar.Snackbar

class UpdatingItemsScreenFragment : Fragment() {

    private var _binding: FragmentUpdatingItemsScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdatingItemsScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = requireActivity().applicationContext
        val firstField = binding.updateFirstParameter
        val secondField = binding.updateSecondParameter
        val thirdField = binding.updateThirdParameter
        val args = UpdatingItemsScreenFragmentArgs.fromBundle(requireArguments())
        val snack = Snackbar.make(
            view,
            "Please fill at least one field to update",
            Snackbar.LENGTH_LONG,
        )
        val v = snack.view
        val params = v.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.CENTER
        v.layoutParams = params

        binding.buttonUpdate.setOnClickListener {
            if (
                firstField.text.isEmpty()
                && secondField.text.isEmpty()
                && thirdField.text.isEmpty()
            ) {
                snack.show()
            } else {
                DatabaseHandler(context)
                    .updateItem(
                        args.identificationNumber,
                        firstField,
                        secondField,
                        thirdField,
                    )

                findNavController().safeNavigate(
                    UpdatingItemsScreenFragmentDirections
                        .actionUpdatingItemsScreenFragmentToMainScreenFragment()
                )
            }
        }
    }

    private fun NavController.safeNavigate(direction: NavDirections) {
        currentDestination?.getAction(direction.actionId)?.run {
            navigate(direction)
        }
    }
}
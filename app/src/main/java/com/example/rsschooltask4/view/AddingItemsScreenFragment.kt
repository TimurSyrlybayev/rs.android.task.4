package com.example.rsschooltask4.view

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.rsschooltask4.data.DatabaseHandler
import com.example.rsschooltask4.data.model.ItemData
import com.example.rsschooltask4.databinding.FragmentAddingItemsScreenBinding
import com.example.rsschooltask4.viewmodel.TaskViewModel
import com.google.android.material.snackbar.Snackbar

class AddingItemsScreenFragment : Fragment() {

    private var _binding: FragmentAddingItemsScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddingItemsScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = requireActivity().applicationContext
        val firstField = binding.firstParameter
        val secondField = binding.secondParameter
        val thirdField = binding.thirdParameter
        val viewModel = TaskViewModel()
        val snack = Snackbar.make(
            view,
            "Please fill at least one field to add record",
            Snackbar.LENGTH_LONG,
        )
        val v = snack.view
        val params = v.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.CENTER
        v.layoutParams = params

        binding.buttonAdd.setOnClickListener {
            if (
                firstField.text.isEmpty()
                && secondField.text.isEmpty()
                && thirdField.text.isEmpty()
            ) {
                snack.show()
            } else {
                val itemData = ItemData(
                    0,
                    firstField.text.toString(),
                    secondField.text.toString(),
                    thirdField.text.toString()
                )
                viewModel.addRecord(itemData)
//                DatabaseHandler(context)
//                    .createItem(firstField, secondField, thirdField)
//                firstField.text.clear()
//                secondField.text.clear()
//                thirdField.text.clear()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
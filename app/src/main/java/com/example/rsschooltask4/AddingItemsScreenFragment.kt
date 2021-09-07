package com.example.rsschooltask4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.rsschooltask4.databinding.FragmentAddingItemsScreenBinding
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

        binding.buttonAdd.setOnClickListener {
            DatabaseHandler(context)
                .createItem(binding.firstParameter, binding.secondParameter, binding.thirdParameter)
            binding.apply {
                firstParameter.text.clear()
                secondParameter.text.clear()
                thirdParameter.text.clear()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
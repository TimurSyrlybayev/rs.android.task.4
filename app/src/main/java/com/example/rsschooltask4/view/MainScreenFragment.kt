package com.example.rsschooltask4.view

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rsschooltask4.R
import com.example.rsschooltask4.data.model.ItemData
import com.example.rsschooltask4.databinding.FragmentMainScreenBinding
import com.example.rsschooltask4.viewmodel.TaskViewModel

class MainScreenFragment : Fragment() {

    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private var adapter: RecyclerAdapter? = null
    private var list: MutableList<ItemData>? = null
    private var viewModel: TaskViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            ExitDialogFragment().show(childFragmentManager, ExitDialogFragment.TAG)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel = TaskViewModel(requireActivity().applicationContext)
        viewModel!!.list.observe(
            viewLifecycleOwner,
            { l ->
                updateRecycler(l)
                binding.noRecordsWarning.isVisible = l.isNullOrEmpty()
            }
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonMoveToAddingItemsScreen.setOnClickListener {
            findNavController().safeNavigate(
                MainScreenFragmentDirections.actionMainScreenFragmentToAddingItemsScreenFragment()
            )
        }
    }

    private fun NavController.safeNavigate(direction: NavDirections) {
        currentDestination?.getAction(direction.actionId)?.run {
            navigate(direction)
        }
    }

    private fun updateRecycler(l: MutableList<ItemData>) {
        list = l
        adapter = RecyclerAdapter(list as ArrayList<ItemData>)
        recyclerView.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private inner class ItemHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var item: ItemData
        private val firstItemParameter: TextView = itemView.findViewById(R.id.card_first_parameter)
        private val secondItemParameter: TextView = itemView.findViewById(R.id.card_second_parameter)
        private val thirdItemParameter: TextView = itemView.findViewById(R.id.card_third_parameter)

        init {
            itemView.findViewById<ImageView>(R.id.delete_icon).setOnClickListener(this)
            itemView.findViewById<ImageView>(R.id.update_icon).setOnClickListener(this)
        }

        fun bind(item: ItemData) {
            this.item = item
            firstItemParameter.text = this.item.first_parameter
            secondItemParameter.text = this.item.second_parameter
            thirdItemParameter.text = this.item.third_parameter
        }

        override fun onClick(v: View?) {
            when (v?.tag) {
                "delete" -> {
                    if (absoluteAdapterPosition != -1) {
                        list!!.removeAt(absoluteAdapterPosition)
                        recyclerView.adapter!!.notifyItemRemoved(absoluteAdapterPosition)
                        viewModel?.deleteRecord(item.id)
                    }
                }
                "update" -> {
                    findNavController()
                        .safeNavigate(
                            MainScreenFragmentDirections
                                .actionMainScreenFragmentToUpdatingItemsScreenFragment(item.id)
                        )
                }
            }
            object : CountDownTimer(300, 300) {
                override fun onTick(millisUntilFinished: Long) {}

                override fun onFinish() {
                    binding.noRecordsWarning.isVisible = list.isNullOrEmpty()
                }
            }.start()
        }
    }

    private inner class RecyclerAdapter(var list: List<ItemData>) : RecyclerView.Adapter<ItemHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
            val view = layoutInflater.inflate(R.layout.item_layout, parent, false)
            return ItemHolder(view)
        }

        override fun onBindViewHolder(holder: ItemHolder, position: Int) {
            val item = list[position]
            holder.bind(item)
        }

        override fun getItemCount(): Int {
            return list.size
        }
    }
}
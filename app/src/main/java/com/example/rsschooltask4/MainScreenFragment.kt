package com.example.rsschooltask4

import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rsschooltask4.databinding.FragmentMainScreenBinding
import com.google.android.material.snackbar.Snackbar

class MainScreenFragment : Fragment() {

    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private var adapter: RecyclerAdapter? = null
    private var list: MutableList<ItemData>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        updateRecycler()

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

    private fun updateRecycler() {
        list = DatabaseHandler(requireContext()).readItem()
        Log.d("List", list.toString())
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
            Log.d("List", "Init")
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
                    list!!.removeAt(absoluteAdapterPosition)
                    recyclerView.adapter!!.notifyItemRemoved(absoluteAdapterPosition)
                    DatabaseHandler(requireContext()).deleteItem(item.id)
                }
                "update" -> {
                    findNavController().safeNavigate(MainScreenFragmentDirections.actionMainScreenFragmentToUpdatingItemsScreenFragment(item.id))
                }
            }

            val snack = Snackbar.make(v!!, "${item.id} pressed", Snackbar.LENGTH_LONG)
            val view = snack.view
            val params = view.layoutParams as FrameLayout.LayoutParams
            params.gravity = Gravity.TOP
            view.layoutParams = params
            snack.show()
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
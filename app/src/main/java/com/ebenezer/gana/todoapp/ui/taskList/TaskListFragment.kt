package com.ebenezer.gana.todoapp.ui.taskList

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ebenezer.gana.todoapp.R
import com.ebenezer.gana.todoapp.SortColumn
import com.ebenezer.gana.todoapp.databinding.FragmentTaskListBinding
import com.ebenezer.gana.todoapp.ui.viewModel.TaskListViewModel
import com.example.roomdatabaseexample.TaskAdapter

class TaskListFragment : Fragment() {


    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TaskListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        with(binding.taskList) {
            layoutManager = LinearLayoutManager(activity)
            adapter = TaskAdapter(requireContext()) {
                findNavController().navigate(
                    TaskListFragmentDirections.actionTaskListFragmentToTaskDetailFragment(it)
                )
            }
        }

        binding.addTask.setOnClickListener {
            findNavController().navigate(
                TaskListFragmentDirections.actionTaskListFragmentToTaskDetailFragment(0))

        }

        viewModel.tasks.observe(viewLifecycleOwner) {
            (binding.taskList.adapter as TaskAdapter).submitList(it)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_sort_priority -> {
                viewModel.changeSortOrder(SortColumn.Priority)
                item.isChecked = true
                true
            }
            R.id.menu_sort_title -> {
                viewModel.changeSortOrder(SortColumn.Title)
                item.isChecked = true
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
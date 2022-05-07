package com.ebenezer.gana.todoapp.ui.taskDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ebenezer.gana.todoapp.PriorityLevel
import com.ebenezer.gana.todoapp.R
import com.ebenezer.gana.todoapp.Task
import com.ebenezer.gana.todoapp.TaskStatus
import com.ebenezer.gana.todoapp.databinding.FragmentTaskDetailBinding
import com.ebenezer.gana.todoapp.ui.viewModel.TaskDetailViewModel

class TaskDetailFragment : Fragment() {


    private var _binding: FragmentTaskDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TaskDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val priorities = mutableListOf<String>()
        PriorityLevel.values().forEach { priorities.add(it.name) }
        val arrayAdapter =
            ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, priorities)
        binding.taskPriority.adapter = arrayAdapter

        binding.taskPriority.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                updateTaskPriorityView(position)
            }
        }
        val id = TaskDetailFragmentArgs.fromBundle(requireArguments()).id
        viewModel.setTaskId(id)

        viewModel.task.observe(viewLifecycleOwner) {
            it?.let { setData(it) }
        }

        binding.saveTask.setOnClickListener {
            saveTask()
        }
        binding.deleteTask.setOnClickListener {
            deleteTask()
        }
    }

    private fun deleteTask() {
        viewModel.deleteTask()
        val action = TaskDetailFragmentDirections.actionTaskDetailFragmentToTaskListFragment()
        this.findNavController().navigate(action)
    }

    private fun saveTask() {
        val title = binding.taskTitle.text.toString()
        val detail = binding.taskDetail.text.toString()
        val priority = binding.taskPriority.selectedItemPosition

        val selectedStatusButton = binding.statusGroup.findViewById<RadioButton>(
            binding.statusGroup.checkedRadioButtonId
        )

        var status = TaskStatus.Open.ordinal
        if (selectedStatusButton.text == TaskStatus.Closed.name) {
            status = TaskStatus.Closed.ordinal
        }

        val task = Task(
            viewModel.taskId.value!!,
            title, detail, priority, status
        )
        viewModel.saveTask(task)
        val action = TaskDetailFragmentDirections.actionTaskDetailFragmentToTaskListFragment()
        this.findNavController().navigate(action)
    }

    private fun setData(task: Task) {
        updateTaskPriorityView(task.priority)
        with(binding) {
            taskTitle.setText(task.title)
            taskDetail.setText(task.details)
        }
        if (task.status == TaskStatus.Open.ordinal) {
            binding.statusOpen.isChecked = true
        } else {
            binding.statusClosed.isChecked = true
        }

        binding.taskPriority.setSelection(task.priority)
    }

    private fun updateTaskPriorityView(priority: Int) {
        when (priority) {
            PriorityLevel.High.ordinal -> {
                binding.taskPriorityView.setBackgroundColor(
                    ContextCompat.getColor(requireActivity(), R.color.colorPriorityHigh)
                )
            }
            PriorityLevel.Medium.ordinal -> {
                binding.taskPriorityView.setBackgroundColor(
                    ContextCompat.getColor(requireActivity(), R.color.colorPriorityMedium)
                )
            }
            else -> binding.taskPriorityView.setBackgroundColor(
                ContextCompat.getColor(requireActivity(), R.color.colorPriorityLow)
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
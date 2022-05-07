package com.ebenezer.gana.todoapp.ui.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.ebenezer.gana.todoapp.Task
import com.ebenezer.gana.todoapp.data.repository.TaskDetailRepository
import kotlinx.coroutines.launch

class TaskDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskDetailRepository = TaskDetailRepository(application)

    private val _taskId = MutableLiveData<Long>(0)

    val taskId: LiveData<Long> = _taskId

    val task: LiveData<Task> = Transformations.switchMap(_taskId) { id ->
        repository.getTask(id)
    }

    fun setTaskId(id: Long) {
        if (_taskId.value != id) {
            _taskId.value = id
        }
    }

    fun saveTask(task: Task) {
        viewModelScope.launch {
            if (_taskId.value == 0L) {
                _taskId.value = repository.insertTask(task)
            } else {
                repository.updateTask(task)
            }
        }
    }

    fun deleteTask() = viewModelScope.launch {
        task.value?.let { repository.deleteTask(it) }
    }
}
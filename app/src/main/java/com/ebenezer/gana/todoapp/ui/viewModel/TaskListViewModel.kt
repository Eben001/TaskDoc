package com.ebenezer.gana.todoapp.ui.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.ebenezer.gana.todoapp.SortColumn
import com.ebenezer.gana.todoapp.Task
import com.ebenezer.gana.todoapp.data.repository.TaskListRepository

class TaskListViewModel(application: Application) : AndroidViewModel(application) {

   /*we are using AndroidViewModel because we require a context to be
    passed to the repository*/

    private val repository: TaskListRepository =
        TaskListRepository(application)

    private val _sortOrder = MutableLiveData<SortColumn>(SortColumn.Priority)

    /*
    Once the value of _sortOrder changes, it calls the getTask from the
    repository using the new _sortOrder
     */
    val tasks: LiveData<List<Task>> = Transformations.switchMap(
        _sortOrder
    ) {
        repository.getTasks(_sortOrder.value!!)
    }


    // Change the task sort order
    fun changeSortOrder(sortOrder: SortColumn){
        _sortOrder.value = sortOrder
    }
}

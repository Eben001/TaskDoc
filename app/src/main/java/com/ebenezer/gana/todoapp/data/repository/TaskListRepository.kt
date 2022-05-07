package com.ebenezer.gana.todoapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.ebenezer.gana.todoapp.SortColumn
import com.ebenezer.gana.todoapp.Task
import com.ebenezer.gana.todoapp.TaskStatus
import com.ebenezer.gana.todoapp.data.database.dao.TaskListDao
import com.ebenezer.gana.todoapp.data.database.TaskDatabase

class TaskListRepository(context: Application) {

    private val taskListDao: TaskListDao = TaskDatabase.getDatabase(context).taskListDao()


    fun getTasks(sort: SortColumn = SortColumn.Priority): LiveData<List<Task>> {
        return if (sort == SortColumn.Priority) {
            taskListDao.getTaskByPriority(TaskStatus.Open.ordinal)
        } else {
            taskListDao.getTaskByTitle(TaskStatus.Open.ordinal)
        }
    }
}
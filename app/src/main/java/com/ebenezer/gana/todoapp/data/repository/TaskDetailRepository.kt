package com.ebenezer.gana.todoapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.ebenezer.gana.todoapp.Task
import com.ebenezer.gana.todoapp.data.database.dao.TaskDetailDao
import com.ebenezer.gana.todoapp.data.database.TaskDatabase

class TaskDetailRepository(context: Application) {

    private val taskDetailDao: TaskDetailDao = TaskDatabase.getDatabase(context)
        .taskDetailsDao()

    fun getTask(id: Long): LiveData<Task> = taskDetailDao.getTask(id)

    suspend fun insertTask(task: Task):Long = taskDetailDao.insertTask(task)

    suspend fun updateTask(task: Task) = taskDetailDao.updateTask(task)

    suspend fun deleteTask(task: Task) = taskDetailDao.deleteTask(task)
}
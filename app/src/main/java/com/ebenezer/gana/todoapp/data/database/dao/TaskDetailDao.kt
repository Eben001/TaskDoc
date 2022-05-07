package com.ebenezer.gana.todoapp.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ebenezer.gana.todoapp.Task

@Dao
interface TaskDetailDao {
    @Query("SELECT * FROM task where id =:id")
    fun getTask(id: Long): LiveData<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task): Long

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

}
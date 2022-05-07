package com.ebenezer.gana.todoapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ebenezer.gana.todoapp.Task
import com.ebenezer.gana.todoapp.data.database.dao.TaskDetailDao
import com.ebenezer.gana.todoapp.data.database.dao.TaskListDao

@Database(entities = [Task::class],
version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {


    abstract fun taskListDao(): TaskListDao
    abstract fun taskDetailsDao(): TaskDetailDao

    companion object {
        private const val DATABASE_NAME = "task_database"


        @Volatile
        private var INSTANCE: TaskDatabase? = null

        /* wrapping to get the database inside the synchronized block means
            only one thread of execution at a time can enter this block of code*/
        fun getDatabase(context: Context): TaskDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                return instance
            }


        }
    }
}
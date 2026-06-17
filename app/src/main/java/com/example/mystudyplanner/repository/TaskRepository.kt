package com.example.mystudyplanner.repository

import androidx.lifecycle.LiveData
import com.example.mystudyplanner.data.AppDatabase
import com.example.mystudyplanner.data.Task
import com.example.mystudyplanner.data.TaskDao

class TaskRepository(private val taskDao: TaskDao) {

    val allTasks: LiveData<List<Task>> = taskDao.getAllTasks()
    val totalCount: LiveData<Int> = taskDao.getTotalCount()
    val completedCount: LiveData<Int> = taskDao.getCompletedCount()
    val pendingCount: LiveData<Int> = taskDao.getPendingCount()

    suspend fun insert(task: Task) {
        taskDao.insertTask(task)
    }

    suspend fun update(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun delete(task: Task) {
        taskDao.deleteTask(task)
    }
}
package com.example.mystudyplanner.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mystudyplanner.data.AppDatabase
import com.example.mystudyplanner.data.Task
import com.example.mystudyplanner.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository
    val allTasks: LiveData<List<Task>>
    val totalCount: LiveData<Int>
    val completedCount: LiveData<Int>
    val pendingCount: LiveData<Int>

    init {
        val taskDao = AppDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
        allTasks = repository.allTasks
        totalCount = repository.totalCount
        completedCount = repository.completedCount
        pendingCount = repository.pendingCount
    }

    fun insert(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(task)
    }

    fun update(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(task)
    }

    fun delete(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(task)
    }

    fun toggleComplete(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(task.copy(isCompleted = !task.isCompleted))
    }
}
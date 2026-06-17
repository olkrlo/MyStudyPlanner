package com.example.mystudyplanner.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mystudyplanner.databinding.ActivityMainBinding
import com.example.mystudyplanner.viewmodel.TaskViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val taskViewModel: TaskViewModel by viewModels()
    private lateinit var taskAdapter: TaskAdapter

    // Launcher untuk AddEditTaskActivity
    private val addEditLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { /* Room LiveData auto-refreshes */ }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()
        setupFab()
    }

    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter(
            onTaskClick = { task ->
                // Edit task
                val intent = Intent(this, AddEditTaskActivity::class.java).apply {
                    putExtra(AddEditTaskActivity.EXTRA_TASK_ID, task.id)
                    putExtra(AddEditTaskActivity.EXTRA_TASK_TITLE, task.title)
                    putExtra(AddEditTaskActivity.EXTRA_TASK_SUBJECT, task.subject)
                    putExtra(AddEditTaskActivity.EXTRA_TASK_DEADLINE, task.deadline)
                    putExtra(AddEditTaskActivity.EXTRA_TASK_PRIORITY, task.priority)
                    putExtra(AddEditTaskActivity.EXTRA_TASK_COMPLETED, task.isCompleted)
                }
                addEditLauncher.launch(intent)
            },
            onCheckClick = { task ->
                taskViewModel.toggleComplete(task)
            },
            onDeleteClick = { task ->
                AlertDialog.Builder(this)
                    .setTitle("Hapus Tugas?")
                    .setMessage("\"${task.title}\" akan dihapus permanen.")
                    .setPositiveButton("Hapus") { _, _ -> taskViewModel.delete(task) }
                    .setNegativeButton("Batal", null)
                    .show()
            }
        )

        binding.rvTasks.apply {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun observeViewModel() {
        taskViewModel.allTasks.observe(this) { tasks ->
            taskAdapter.submitList(tasks)
            binding.tvEmptyState.visibility =
                if (tasks.isEmpty()) android.view.View.VISIBLE else android.view.View.GONE
        }

        taskViewModel.totalCount.observe(this) { count ->
            binding.tvTotalCount.text = count.toString()
        }

        taskViewModel.completedCount.observe(this) { count ->
            binding.tvCompletedCount.text = count.toString()
        }

        taskViewModel.pendingCount.observe(this) { count ->
            binding.tvPendingCount.text = count.toString()
        }
    }

    private fun setupFab() {
        binding.fabAddTask.setOnClickListener {
            val intent = Intent(this, AddEditTaskActivity::class.java)
            addEditLauncher.launch(intent)
        }
    }
}
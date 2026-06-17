package com.example.mystudyplanner.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mystudyplanner.R
import com.example.mystudyplanner.data.Task
import com.example.mystudyplanner.databinding.ActivityAddEditTaskBinding
import com.example.mystudyplanner.viewmodel.TaskViewModel
import java.util.Calendar

class AddEditTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditTaskBinding
    private val taskViewModel: TaskViewModel by viewModels()

    private var taskId: Int = -1
    private var isCompleted: Boolean = false

    companion object {
        const val EXTRA_TASK_ID        = "extra_task_id"
        const val EXTRA_TASK_TITLE     = "extra_task_title"
        const val EXTRA_TASK_SUBJECT   = "extra_task_subject"
        const val EXTRA_TASK_DEADLINE  = "extra_task_deadline"
        const val EXTRA_TASK_PRIORITY  = "extra_task_priority"
        const val EXTRA_TASK_COMPLETED = "extra_task_completed"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupPrioritySpinner()
        setupDatePicker()
        populateIfEditing()
        setupSaveButton()
    }

    private fun setupToolbar() {
        val isEdit = intent.hasExtra(EXTRA_TASK_ID)
        binding.tvHeader.text = if (isEdit) "✏️ Edit Tugas" else "✨ Tugas Baru"
        binding.btnBack.setOnClickListener { finish() }
    }

    private fun setupPrioritySpinner() {
        val priorities = listOf("Low", "Medium", "High")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, priorities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPriority.adapter = adapter
    }

    private fun setupDatePicker() {
        binding.etDeadline.setOnClickListener {
            val cal = Calendar.getInstance()
            DatePickerDialog(
                this,
                R.style.DatePickerTheme,
                { _, year, month, day ->
                    binding.etDeadline.setText(
                        String.format("%02d/%02d/%04d", day, month + 1, year)
                    )
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun populateIfEditing() {
        taskId = intent.getIntExtra(EXTRA_TASK_ID, -1)
        if (taskId != -1) {
            binding.etTitle.setText(intent.getStringExtra(EXTRA_TASK_TITLE))
            binding.etSubject.setText(intent.getStringExtra(EXTRA_TASK_SUBJECT))
            binding.etDeadline.setText(intent.getStringExtra(EXTRA_TASK_DEADLINE))
            isCompleted = intent.getBooleanExtra(EXTRA_TASK_COMPLETED, false)

            val priority = intent.getStringExtra(EXTRA_TASK_PRIORITY) ?: "Low"
            val idx = listOf("Low", "Medium", "High").indexOf(priority)
            if (idx >= 0) binding.spinnerPriority.setSelection(idx)

            binding.btnSave.text = "💾 Simpan Perubahan"
        }
    }

    private fun setupSaveButton() {
        binding.btnSave.setOnClickListener {
            val title    = binding.etTitle.text.toString().trim()
            val subject  = binding.etSubject.text.toString().trim()
            val deadline = binding.etDeadline.text.toString().trim()
            val priority = binding.spinnerPriority.selectedItem.toString()

            if (title.isEmpty()) {
                binding.etTitle.error = "Judul tidak boleh kosong"
                return@setOnClickListener
            }
            if (subject.isEmpty()) {
                binding.etSubject.error = "Mata kuliah tidak boleh kosong"
                return@setOnClickListener
            }
            if (deadline.isEmpty()) {
                Toast.makeText(this, "Pilih deadline dulu ya! 📅", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val task = Task(
                id          = if (taskId != -1) taskId else 0,
                title       = title,
                subject     = subject,
                deadline    = deadline,
                priority    = priority,
                isCompleted = isCompleted
            )

            if (taskId != -1) {
                taskViewModel.update(task)
                Toast.makeText(this, "Tugas berhasil diperbarui! ✅", Toast.LENGTH_SHORT).show()
            } else {
                taskViewModel.insert(task)
                Toast.makeText(this, "Tugas berhasil ditambahkan! 🎉", Toast.LENGTH_SHORT).show()
            }
            finish()
        }
    }
}
package com.rachellima.todolist.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.rachellima.todolist.databinding.ActivityCreateTaskBinding
import com.rachellima.todolist.datasource.TaskDataSource
import com.rachellima.todolist.extensions.format
import com.rachellima.todolist.extensions.text
import com.rachellima.todolist.model.Task
import java.util.*

class CreateTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        insertListeners()
    }

    private fun insertListeners() {
        binding.textInputDate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val timezone = TimeZone.getDefault()
                val offset = timezone.getOffset(Date().time) * -1
                binding.textInputDate.text = Date(it + offset).format()
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }

        binding.tilHour.editText?.setOnClickListener {
            val timerPicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()

            timerPicker.addOnPositiveButtonClickListener {
                val minute = if (timerPicker.minute in 0..9 )"0${timerPicker.minute}" else "${timerPicker.minute}"
                val hour = if (timerPicker.hour in 0..9 )"0${timerPicker.hour}" else "${timerPicker.hour}"
                binding.tilHour.text = "${hour}:${minute}"
            }

            timerPicker.show(supportFragmentManager, null)

        }

        binding.cancel.setOnClickListener {
            finish()
        }

        binding.createTask.setOnClickListener {
            Log.e("TAG", "Click")
            val task = Task(
                title = binding.textInputTitle.text,
                hour = binding.tilHour.text,
                date = binding.textInputDate.text,
                description = binding.description.text
            )
            TaskDataSource.insertTask(task)
            Log.e("TAG", "add " + TaskDataSource.getList())
        }


    }


}
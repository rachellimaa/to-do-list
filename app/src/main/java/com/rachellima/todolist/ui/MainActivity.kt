package com.rachellima.todolist.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.rachellima.todolist.databinding.ActivityMainBinding
import com.rachellima.todolist.datasource.TaskDataSource

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { TaskListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvTasks.adapter = adapter
        updateList()
        insertListeners()
    }

    private fun insertListeners() {
        binding.floatingButton.setOnClickListener {
            startActivityForResult(Intent(this, CreateTaskActivity::class.java), CREATE_NEW_TASK)

            adapter.listenerEdit = {
                val intent = Intent(this, CreateTaskActivity::class.java)
                intent.putExtra(CreateTaskActivity.TASK_ID, it.id)
                startActivityForResult(intent, CREATE_NEW_TASK)
            }

            adapter.listenerDelete = {
                TaskDataSource.deleteTask(it)
                updateList()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_NEW_TASK && resultCode == Activity.RESULT_OK) updateList()
    }

    private fun updateList() {
        val list = TaskDataSource.getList()
        binding.emptyStateLayout.emptyState.visibility = if (list.isEmpty())
            View.VISIBLE
        else
            View.GONE

        adapter.submitList(list)
    }

    companion object {
        private const val CREATE_NEW_TASK = 1000
    }
}
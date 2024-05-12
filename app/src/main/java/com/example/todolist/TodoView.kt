package com.example.todolist

import TasksAdapter
import UpdateTask
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TodoView : AppCompatActivity() {

    private lateinit var recyclerViewTasks: RecyclerView
    private lateinit var tasksAdapter: TasksAdapter
    private lateinit var databaseHelper: TasksDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_todo_view)

        recyclerViewTasks = findViewById(R.id.recyclerViewTasks)
        recyclerViewTasks.layoutManager = LinearLayoutManager(this)
        tasksAdapter = TasksAdapter(emptyList(),this) // Initialize with an empty list
        recyclerViewTasks.adapter = tasksAdapter

        databaseHelper = TasksDatabaseHelper(
            this,
            TasksDatabaseHelper.DATABASE_VERSION,
            TasksDatabaseHelper.DATABASE_NAME
        )

        fetchDataAndUpdateUI()


        val addButton = findViewById<FloatingActionButton>(R.id.addButton)
        addButton.setOnClickListener {
            // Start AddTask activity when the FloatingActionButton is clicked
            val intent = Intent(this, AddTask::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        fetchDataAndUpdateUI()
    }

    fun onItemClick(task: Task) {
        // Handle item click here (if needed)
    }

    fun onEditClick(task: Task) {
        // Handle edit click here (if needed)
        val intent = Intent(this, UpdateTask::class.java)
        intent.putExtra("taskId", task.id) // Pass the task ID to the edit activity
        startActivity(intent)
    }

    fun onDeleteClick(task: Task) {
        databaseHelper.deleteTask(task.id)
        fetchDataAndUpdateUI()
    }


    private fun fetchDataAndUpdateUI() {
        val tasksList = databaseHelper.getAllTasks()
        tasksAdapter.refreshData(tasksList)
    }



}

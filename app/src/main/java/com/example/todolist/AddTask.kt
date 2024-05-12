package com.example.todolist

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton


class AddTask : AppCompatActivity() {

    private lateinit var databaseHelper: TasksDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_task)

        databaseHelper = TasksDatabaseHelper(this, TasksDatabaseHelper.DATABASE_VERSION, TasksDatabaseHelper.DATABASE_NAME)

        val saveButton = findViewById<Button>(R.id.saveButton)
        val taskNameEditText = findViewById<EditText>(R.id.taskNameEditText)
        saveButton.setOnClickListener {
            val taskTitle = taskNameEditText.text.toString().trim()
            if (taskTitle.isNotEmpty()) {
                val newTask = Task(0, taskTitle) // Assuming 0 for id as it's auto-incremented
                databaseHelper.insertNote(newTask)
                Toast.makeText(this, "Save Task", Toast.LENGTH_SHORT).show()
                finish() // Close the activity after insertion
            } else {
                // Handle empty task title case
            }
        }
    }

}
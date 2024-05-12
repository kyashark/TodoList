import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todolist.R
import com.example.todolist.Task
import com.example.todolist.TasksDatabaseHelper

class UpdateTask : AppCompatActivity() {
    private lateinit var databaseHelper: TasksDatabaseHelper
    private lateinit var taskNameEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update_task)

        // Initialize database helper
        databaseHelper = TasksDatabaseHelper(this, TasksDatabaseHelper.DATABASE_VERSION, TasksDatabaseHelper.DATABASE_NAME)

        // Find and initialize UI components
        taskNameEditText = findViewById(R.id.taskUpdateNameEditText)
        val saveUpdateButton: Button = findViewById(R.id.saveUpdateButton)

        // Set onClickListener for the save button
        saveUpdateButton.setOnClickListener {
            updateTaskInDatabase()
        }

        // Apply padding to handle system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun updateTaskInDatabase() {
        val taskId = intent.getIntExtra("task_id", -1) // Get task ID from intent extras
        val taskTitle = taskNameEditText.text.toString().trim()

        if (taskId != -1 && taskTitle.isNotEmpty()) {
            val updatedTask = Task(taskId, taskTitle)
            databaseHelper.updateTask(updatedTask)
            finish() // Close the activity after updating task
        } else {
            // Handle invalid task ID or empty task title
        }
    }
}

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.Task
import com.example.todolist.TodoView

class TasksAdapter(private var tasksList: List<Task>, private val itemClickListener: TodoView) :
    RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val taskTitleTextView: TextView = itemView.findViewById(R.id.textViewTaskName)
        val editImageView: ImageView = itemView.findViewById(R.id.editbtn)
        val deleteImageView: ImageView = itemView.findViewById(R.id.deletebtn)

        init {
            itemView.setOnClickListener(this)
            editImageView.setOnClickListener(this)
            deleteImageView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                when (v?.id) {
                    R.id.editbtn -> itemClickListener.onEditClick(tasksList[position])
                    R.id.deletebtn -> itemClickListener.onDeleteClick(tasksList[position])
                    else -> itemClickListener.onItemClick(tasksList[position])
                }
            }
        }

        fun bind(task: Task) {
            taskTitleTextView.text = task.task
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasksList[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int {
        return tasksList.size
    }

    fun refreshData(newTasksList: List<Task>) {
        tasksList = newTasksList
        notifyDataSetChanged()
    }
}

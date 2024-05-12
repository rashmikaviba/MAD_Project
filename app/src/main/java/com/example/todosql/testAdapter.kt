package com.example.todosql

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class testAdapter(private var tasks: List<Task>, context: Context) :
    RecyclerView.Adapter<testAdapter.TaskViewHolder>() {


private val db:TaskDatabaseHelper = TaskDatabaseHelper(context)

    class TaskViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val titleText:TextView = itemView.findViewById(R.id.tittleText)
        val contentText:TextView = itemView.findViewById(R.id.contentText)
        val delete:ImageView = itemView.findViewById(R.id.delete)
        val update:ImageView = itemView.findViewById(R.id.update)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item,parent,false)


        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int = tasks.size




    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.titleText.text = task.Task_t
        holder.contentText.text = task.description

        holder.update.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateTaskActivity::class.java).apply {
                putExtra("Task_Id", task.Task_ID)
            }
            holder.itemView.context.startActivity(intent) // Use startActivity instead of startActivities
        }

        holder.delete.setOnClickListener{
            db.deleteTask(task.Task_ID)
            refreshData(db.getAllNote())
            Toast.makeText(holder.itemView.context,"note deleted",Toast.LENGTH_SHORT).show()
        }
    }



            fun refreshData(newTask: List<Task>) {
                tasks = newTask
                notifyDataSetChanged()
            }


        }
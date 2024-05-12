package com.example.todosql

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.todosql.databinding.ActivityUpdateTaskBinding

class UpdateTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateTaskBinding
    private lateinit var db: TaskDatabaseHelper
    private var taskID: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TaskDatabaseHelper(this)

        taskID = intent.getIntExtra("Task_ID", -1)
        if (taskID == -1) {
            finish() // Finish the activity if taskID is invalid
            return
        }


        val task = db.getNoteById(taskID)
        binding.updateTittle.setText(task.Task_ID) // Set the title EditText to the task's title
        binding.updateDes.setText(task.description) // Set the description EditText to the task's description


        binding.updateSaveButton.setOnClickListener(){
            val newTittle = binding.updateTittle.text.toString()
            val newContent = binding.updateDes.text.toString()
            val updateNote = Task(taskID,newTittle,newContent)
            db.updateNote(updateNote)
            finish()
            Toast.makeText(this, "change save", Toast.LENGTH_SHORT).show()
        }

    }
}

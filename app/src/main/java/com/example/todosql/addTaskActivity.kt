package com.example.todosql

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.todosql.databinding.ActivityAddTaskBinding
import kotlin.math.log

class addTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var db:TaskDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = TaskDatabaseHelper(this)
        val saveBtn = findViewById<ImageView>(R.id.saveButton)
        val title = findViewById<EditText>(R.id.tittleText)
        val des = findViewById<EditText>(R.id.desText)
        saveBtn.setOnClickListener(){
            val Task_t = binding.tittleText.text.toString()
            val description= binding.desText.text.toString()
            val task = Task(0,Task_t,description)
            db.insertNote(task)
            finish()
            Toast.makeText(this,"Task Saved",Toast.LENGTH_SHORT).show()
        }


    }
}
package com.example.todosql

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todosql.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: TaskDatabaseHelper
    private lateinit var testAdapter: testAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TaskDatabaseHelper(this)
        testAdapter = testAdapter(db.getAllNote(),this)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = testAdapter

        binding.addButton.setOnClickListener{
            val intent = Intent(this,addTaskActivity::class.java)
            startActivity(intent)

        }
    }

    override fun onResume() {
        super.onResume()
        testAdapter.refreshData(db.getAllNote())
    }
}
package com.example.todosql

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TaskDatabaseHelper(context:Context): SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION)  {

    companion object{
        private const val DATABASE_NAME = "Tasksapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allTask"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITTLE = "Task_t"
        private const val COLUMN_DESCRIPTION = "description"
        }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY,$COLUMN_TITTLE TEXT,$COLUMN_DESCRIPTION TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertNote(task: Task){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITTLE, task.Task_t)
            put(COLUMN_DESCRIPTION, task.description)
        }
        db.insert(TABLE_NAME,null,values)
        db.close()

    }

    fun getAllNote(): List<Task> {
        val taskList = mutableListOf<Task>() // Corrected variable name to follow Kotlin naming conventions
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val Task_ID = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val Task_t = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITTLE))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))


            val task = Task(Task_ID, Task_t, description) // Corrected variable name and use of Task constructor
            taskList.add(task) // Corrected usage to add 'task' object instead of 'Task' class
        }

        cursor.close()
        db.close()
        return taskList
    }

    fun updateNote(task: Task){
        val db = readableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITTLE,task.Task_t)
            put(COLUMN_DESCRIPTION,task.description)

        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(task.Task_ID.toString())
        db.update(TABLE_NAME,values,whereClause,whereArgs)
        db.close()
    }

    fun getNoteById(TaskID:Int): Task{
        val db = readableDatabase
        val query =  "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID=$TaskID"
        val cursor = db.rawQuery(query,null)
        cursor.moveToFirst()

        val Task_ID = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val Task_t = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITTLE))
        val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))

        cursor.close()
        db.close()
        return Task(Task_ID,Task_t,description)

    }

    fun deleteTask(TaskID: Int){
        val db = readableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(TaskID.toString())
        db.delete(TABLE_NAME,whereClause,whereArgs)
        db.close()
    }

}
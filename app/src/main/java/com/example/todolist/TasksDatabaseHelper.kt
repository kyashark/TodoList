package com.example.todolist

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.Cursor

class TasksDatabaseHelper(context: Context, DATABASE_VERSION: Int, DATABASE_NAME: String?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "notesapp.db"
        const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allnotes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertNote(task: Task) {
        writableDatabase.use { db ->
            val values = ContentValues().apply {
                put(COLUMN_TITLE, task.task)

            }
            db.insert(TABLE_NAME, null, values)
        }
    }

    fun getAllTasks(): List<Task> {
        val tasksList = mutableListOf<Task>()
        val selectQuery = "SELECT * FROM $TABLE_NAME"

        readableDatabase.use { db ->
            val cursor = db.rawQuery(selectQuery, null)
            cursor.use {
                if (it.moveToFirst()) {
                    val idIndex = it.getColumnIndex(COLUMN_ID)
                    val titleIndex = it.getColumnIndex(COLUMN_TITLE)

                    while (!it.isAfterLast) {
                        val id = if (idIndex != -1) it.getInt(idIndex) else -1
                        val taskTitle = if (titleIndex != -1) it.getString(titleIndex) else "Unknown Task"
                        val task = Task(id, taskTitle)
                        tasksList.add(task)
                        it.moveToNext()
                    }
                }
            }
        }
        return tasksList
    }

    fun updateTask(task: Task) {
        writableDatabase.use { db ->
            val values = ContentValues().apply {
                put(COLUMN_TITLE, task.task)
            }
            val whereClause = "$COLUMN_ID = ?"
            val whereArgs = arrayOf(task.id.toString())
            db.update(TABLE_NAME, values, whereClause, whereArgs)
        }
    }

    fun getTaskById(id: Int): Task? {
        var task: Task? = null
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = ?"

        readableDatabase.use { db ->
            val cursor = db.rawQuery(selectQuery, arrayOf(id.toString()))
            cursor.use {
                if (it.moveToFirst()) {
                    val idIndex = it.getColumnIndex(COLUMN_ID)
                    val titleIndex = it.getColumnIndex(COLUMN_TITLE)

                    // Check if the column indices are valid
                    if (idIndex != -1 && titleIndex != -1) {
                        val taskId = it.getInt(idIndex)
                        val taskTitle = it.getString(titleIndex)
                        task = Task(taskId, taskTitle)
                    } else {
                        // Handle the case when column indices are not valid
                        // For example, log an error or return a default Task object
                    }
                }
            }
        }
        return task
    }

    fun deleteTask(taskId: Int): Boolean {
        writableDatabase.use { db ->
            val whereClause = "$COLUMN_ID = ?"
            val whereArgs = arrayOf(taskId.toString())
            val deletedRows = db.delete(TABLE_NAME, whereClause, whereArgs)
            return deletedRows > 0
        }
    }



}
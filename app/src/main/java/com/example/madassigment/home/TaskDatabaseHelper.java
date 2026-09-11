package com.example.madassigment.home; // Adjust to your package

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class TaskDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "todo_database.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_TASKS = "tasks";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TASK_NAME = "task_name";

    public TaskDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the tasks table
        String createTable = "CREATE TABLE " + TABLE_TASKS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TASK_NAME + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    // --- Method to Add a Task ---
    public void addTask(String taskName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_NAME, taskName);

        db.insert(TABLE_TASKS, null, values);
        db.close();
    }

    // --- Method to Get All Tasks ---
    public List<String> getAllTasks() {
        List<String> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to select all tasks
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TASKS, null);

        if (cursor.moveToFirst()) {
            do {
                // Get the task name from the cursor (Column index 1 is task_name)
                String task = cursor.getString(1);
                taskList.add(task);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return taskList;
    }
}
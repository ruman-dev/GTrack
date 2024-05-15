package com.rumanweb.goaltrackerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "goaltracker";
    private static final int DB_VERSION = 6;
    private static final String TABLE_NAME = "tasklist";
    private static final String ID_COL = "id";
    private static final String NAME_COL = "tasktitle";
    private static final String DUEDATE = "duedate";
    private static final String STEPSCOUNT = "stepscount";
    private static final String STEPSTEXT = "stepstext";
    private static final String NOTES = "notes";
    private static final String CREATIONDATE = "creationdate";
    private static final String PROGRESS = "progress";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + DUEDATE + " TEXT,"
                + STEPSCOUNT + " TEXT,"
                + STEPSTEXT + " TEXT,"
                + NOTES + " TEXT,"
                + CREATIONDATE + " TEXT,"
                + PROGRESS + " INTEGER)";
        db.execSQL(query);
    }

    public void deleteTask(String taskTitle) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, NAME_COL + "=?", new String[]{taskTitle});
        db.close();
    }
    public void updateProgress(String taskTitle, int progress) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PROGRESS, progress);
        db.update(TABLE_NAME, values, NAME_COL + "=?", new String[]{taskTitle});
        db.close();
    }

    public void updateTask(String originalTaskTitle, String taskTitle, String duedate, String stepsCount, String stepsText, String notes, String creationDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COL, taskTitle);
        values.put(DUEDATE, duedate);
        values.put(STEPSCOUNT, stepsCount);
        values.put(STEPSTEXT, stepsText);
        values.put(NOTES, notes);
        values.put(CREATIONDATE, creationDate);
        db.update(TABLE_NAME, values, NAME_COL + "=?", new String[]{originalTaskTitle});
        db.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addNewTask(String tasktitle, String duedate, String stepsCount, String stepsText, String notes, String creationDate){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
//      String query = "INSERT INTO " + TABLE_NAME + " (" + NAME_COL + ", " + DUEDATE + ", " + NOTES + ") VALUES ('" + tasktitle + "', '" + duedate + "', '" + notes + "')";

        values.put(NAME_COL, tasktitle);
        values.put(DUEDATE, duedate);
        values.put(STEPSCOUNT, stepsCount);
        values.put(STEPSTEXT, stepsText);
        values.put(NOTES, notes);
        values.put(CREATIONDATE, creationDate);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public ArrayList<TaskModal> readTasks()
    {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        ArrayList<TaskModal> taskList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                taskList.add(new TaskModal(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getInt(7)));

//                        cursor.getString(cursor.getColumnIndex(NAME_COL)),
//                        cursor.getString(cursor.getColumnIndex(DUEDATE)),
//                        cursor.getString(cursor.getColumnIndex(NOTES))
            } while (cursor.moveToNext());
        }
        cursor.close();
        return taskList;
    }
}

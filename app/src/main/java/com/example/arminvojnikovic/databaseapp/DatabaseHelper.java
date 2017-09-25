package com.example.arminvojnikovic.databaseapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Armin Vojnikovic on 2017-03-21.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "students.db";
    public static final String TABLE_NAME = "students_table";
    public static final String COLUMN1 = "Name";
    public static final String COLUMN2 = "LastName";
    public static final String COLUMN3 = "Grade";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,Name TEXT,LastName TEXT, Grade INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }



    public boolean insertData(String name,String lastname,String grade){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN1,name);
        contentValues.put(COLUMN2,lastname);
        contentValues.put(COLUMN3,grade);
        long result = db.insert(TABLE_NAME,null,contentValues);

        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public boolean deleteData(String name){
        SQLiteDatabase db = this.getWritableDatabase();

        //String table = "students_table";

        long result = db.delete(TABLE_NAME,"name=?",new String[]{name});

        if(result == 0){
            return false;
        }else {
            return true;
        }
    }
}

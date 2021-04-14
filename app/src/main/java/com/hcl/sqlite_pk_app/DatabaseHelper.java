package com.hcl.sqlite_pk_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "students.db";
    public static final String TABLE_NAME = "student_details";
    public static final String ID = "ID";
    public static final String FNAME = "FNAME";
    public static final String LNAME = "LNAME";
    public static final String SCORE = "SCORE";



    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " +TABLE_NAME
                + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, FNAME TEXT, LNAME TEXT,SCORE INTEGER )");




    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public boolean insertData(String fname,String lname,String score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FNAME,fname);
        contentValues.put(LNAME,lname);
        contentValues.put(SCORE,score);
        long insertdt = db.insert(TABLE_NAME,null,contentValues);
        if(insertdt == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cr= db.rawQuery("select * from " + TABLE_NAME,null);
        return cr;
    }


    public Integer deleteData(String id){
      SQLiteDatabase db = this.getWritableDatabase();
      return db.delete(TABLE_NAME, "ID =?", new String[] {id});

    }
}

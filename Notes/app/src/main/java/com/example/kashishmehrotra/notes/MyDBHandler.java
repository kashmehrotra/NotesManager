package com.example.kashishmehrotra.notes;

/**
 * Created by Kashish Mehrotra on 02-May-15.
 */

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "notes.db";
    public static final String TABLE_NOTES = "notes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "_title";
    public static final String COLUMN_CONTENT = "_content";
    public static final String COLUMN_PRIORITY = "_priority";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query= "CREATE TABLE " + TABLE_NOTES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_CONTENT + " TEXT, " +
                COLUMN_PRIORITY + " INTEGER " +
                ");";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES + ";");
        onCreate(db);
    }

    public void addRow(NotesManager notes){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, notes.get_title());
        values.put(COLUMN_CONTENT, notes.get_content());
        values.put(COLUMN_PRIORITY, notes.get_priority());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NOTES, null, values);
        db.close();
    }

    public void deleteRow(User user){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM "+ TABLE_NOTES + " WHERE " + COLUMN_TITLE + "=\"" + user.title + "\" AND "
                + COLUMN_CONTENT + "=\"" + user.content + "\" AND "
                + COLUMN_PRIORITY + "=\"" + user.priority + "\";");
    }

    public ArrayList<User> fetchRows(){
        String query = "SELECT * FROM " + TABLE_NOTES + ";";
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<User> listUser = new ArrayList<User>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            listUser.add(new User(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_PRIORITY))));
        }
        return listUser;
    }

    public int checkDuplicate(NotesManager notesManager){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_NOTES + " WHERE " + COLUMN_TITLE + "=\"" + notesManager.get_title() + "\" AND "
                + COLUMN_CONTENT + "=\"" + notesManager.get_content() + "\" AND "
                + COLUMN_PRIORITY + "=\"" + notesManager.get_priority() + "\";";
        Cursor cursor = db.rawQuery(query, null);
        return cursor.getCount();
    }

    public ArrayList<String> fetchRow(int a){
        String query = "SELECT * FROM " + TABLE_NOTES + " WHERE " + COLUMN_PRIORITY + "=" + a;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<String> listUser = new ArrayList<String>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            listUser.add(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
        }
        return listUser;
    }

}

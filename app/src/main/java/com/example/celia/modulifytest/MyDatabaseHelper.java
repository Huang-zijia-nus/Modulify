package com.example.celia.modulifytest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Celia on 6/8/2017.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_TASK = "create table task ("
            + "id integer primary key autoincrement, "
            + "code text, "
            + "name text, "
            + "deadline_dd integer, "
            + "deadline_mm integer, "
            +"deadline_yyyy integer, "
            + "difficulty integer)";

    private Context mContext;
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory
            factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TASK);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

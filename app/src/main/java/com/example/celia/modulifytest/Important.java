package com.example.celia.modulifytest;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.sql.SQLClientInfoException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Important extends Fragment {
    private ListView listview;
    private List<Task> tsks;
    private MyDatabaseHelper dbHelper;
    SQLiteDatabase db;
    private tsk_compare compare;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_important, container, false);
        tsks=new ArrayList<>();
        compare=new tsk_compare();
        getData();
        Collections.sort(tsks,compare);


        count_list_adapter adapter=new count_list_adapter(getActivity(),R.layout.each_task,tsks);
        listview=(ListView)v.findViewById(R.id.count_down_listview);
        listview.setAdapter(adapter);
        return v;
    }
    private void getData(){
        dbHelper=new MyDatabaseHelper(getActivity(),"TasksStore.db",null,1);
        db=dbHelper.getWritableDatabase();
        Cursor cursor=db.query("task",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String mod_code=cursor.getString(cursor.getColumnIndex("code"));
                String tsk_type=cursor.getString(cursor.getColumnIndex("name"));
                int DD=cursor.getInt(cursor.getColumnIndex("deadline_dd"));
                int MM=cursor.getInt(cursor.getColumnIndex("deadline_mm"));
                int YYYY=cursor.getInt(cursor.getColumnIndex("deadline_yyyy"));
                int diff_pt=cursor.getInt(cursor.getColumnIndex("difficulty"));
                tsks.add(new Task(mod_code,tsk_type,diff_pt,DD,MM,YYYY));
            }while(cursor.moveToNext());
        }
    }
}

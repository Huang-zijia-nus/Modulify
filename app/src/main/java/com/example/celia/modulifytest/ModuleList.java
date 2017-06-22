package com.example.celia.modulifytest;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class ModuleList extends Fragment {
    private ArrayList<String> mods;
    private MyDatabaseHelper dbHelper;
    private HashMap<String,Boolean> containsMod;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_module_list, container, false);
        mods=new ArrayList<>();
        containsMod=new HashMap<>();
        getData();
        Collections.sort(mods);

        ArrayAdapter<String> mod_adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,mods);
        ListView mod_listview=(ListView)v.findViewById(R.id.listview);
        mod_listview.setAdapter(mod_adapter);

        mod_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),DetailList.class);
                intent.putExtra("module_code",mods.get(position));
                startActivity(intent);
            }
        });

        return v;
    }
    private void getData(){
        dbHelper=new MyDatabaseHelper(getActivity(),"TasksStore.db",null,1);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.query("task",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String mod_code=cursor.getString(cursor.getColumnIndex("code"));
                if(!containsMod.containsKey(mod_code)) {
                    containsMod.put(mod_code, true);
                    mods.add(mod_code);
                }
            }while(cursor.moveToNext());
        }
    }
}

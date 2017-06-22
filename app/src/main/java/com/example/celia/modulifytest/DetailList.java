package com.example.celia.modulifytest;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class DetailList extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    ArrayList<Task> tsks;
    String module_code;
    String[] ops={"select","Monday","Tuesday","Wednesday","Thursday","Friday"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_detail_list);
        tsks=new ArrayList<>();
        Intent intent=getIntent();
        module_code=intent.getStringExtra("module_code");
        TextView reminder=(TextView)findViewById(R.id.empty_reminder);
        TextView title=(TextView)findViewById(R.id.in_detail_list_title);
        title.setText(module_code);

        setTitle(module_code);

        getData();
        tsk_compare compare=new tsk_compare();
        Collections.sort(tsks,compare);

        if(tsks.isEmpty()) reminder.setVisibility(View.VISIBLE);
        else reminder.setVisibility(View.GONE);

        each_mod_list_adapter adapter=new each_mod_list_adapter(this,R.layout.in_module_list_each_task,tsks);
        ListView listView=(ListView)findViewById(R.id.detail_list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task tsk=tsks.get(position);
                Intent intent=new Intent(DetailList.this,each_tsk_detail.class);
                intent.putExtra("module_code",tsk.getMod());
                intent.putExtra("task_type",tsk.getType());
                intent.putExtra("diff_level",tsk.getDiffLevel());
                if(!tsk.getMM().equals("0")&&!tsk.getYY().equals("0"))intent.putExtra("date",tsk.getDD()+"/"+tsk.getMM()+"/"+tsk.getYY());
                else intent.putExtra("date",ops[Integer.parseInt(tsk.getDD())]);
                intent.putExtra("DaysLeft",tsk.getDay());
                startActivity(intent);
            }
        });
        Button return_button=(Button)findViewById(R.id.detail_list_return_button);
        return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DetailList.this,CountDown.class);
                intent.putExtra("reminder","true");
                startActivity(intent);
                finish();
            }
        });

    }
    private void getData(){
        dbHelper=new MyDatabaseHelper(this,"TasksStore.db",null,1);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.query("task",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String mod_code=cursor.getString(cursor.getColumnIndex("code"));
                if(mod_code.equals(module_code)) {
                    String tsk_type = cursor.getString(cursor.getColumnIndex("name"));
                    int DD = cursor.getInt(cursor.getColumnIndex("deadline_dd"));
                    int MM = cursor.getInt(cursor.getColumnIndex("deadline_mm"));
                    int YYYY = cursor.getInt(cursor.getColumnIndex("deadline_yyyy"));
                    int diff_pt = cursor.getInt(cursor.getColumnIndex("difficulty"));
                    tsks.add(new Task(mod_code, tsk_type, diff_pt, DD, MM, YYYY));
                }
            }while(cursor.moveToNext());
        }
    }
}

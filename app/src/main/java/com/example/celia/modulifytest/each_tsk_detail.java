package com.example.celia.modulifytest;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class each_tsk_detail extends AppCompatActivity {
    private String mod_code,tsk_type,date,diff,day;
    private int diff_level;
    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private TextView deleted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_each_tsk_detail);
        Intent intent=getIntent();
        dbHelper=new MyDatabaseHelper(this,"TasksStore.db",null,1);
        db=dbHelper.getWritableDatabase();

        day=intent.getStringExtra("DaysLeft");
        mod_code=intent.getStringExtra("module_code");
        tsk_type=intent.getStringExtra("task_type");
        diff_level=Integer.parseInt(intent.getStringExtra("diff_level"));
        date=intent.getStringExtra("date");
        if(diff_level==1) diff="easy";
        else if(diff_level==3) diff="medium";
        else diff="hard";
        TextView daysLeft=(TextView)findViewById(R.id.each_tsk_days_left);
        daysLeft.setText(day);

        final TextView module_code=(TextView)findViewById(R.id.each_tsk_enter_mod_code);
        module_code.setText(mod_code);

        TextView task_type=(TextView)findViewById(R.id.each_tsk_task_select);
        task_type.setText(tsk_type);

        TextView ddl=(TextView)findViewById(R.id.each_tsk_date);
        ddl.setText(date);

        TextView difficulty=(TextView)findViewById(R.id.each_tsk_select_diff_level);
        difficulty.setText(diff);

        Button back_button=(Button)findViewById(R.id.in_each_tsk_detail_back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(each_tsk_detail.this,DetailList.class);
                intent.putExtra("module_code",mod_code);
                startActivity(intent);
                finish();
            }
        });

        Button delete_button=(Button)findViewById(R.id.delete_tsk);
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = db.query("task", null, null, null, null, null, null);
                Integer taskId = -1;
                if(cursor.moveToFirst()) {
                    do{
                        String cursor_mod_code = cursor.getString(cursor.getColumnIndex("code"));
                        String cursor_tsk_type = cursor.getString(cursor.getColumnIndex("name"));
                        if(cursor_mod_code.equals(mod_code) && cursor_tsk_type.equals(tsk_type)){
                            taskId = cursor.getInt(cursor.getColumnIndex("id"));
                            break;
                        }
                    }while(cursor.moveToNext());
                }
                Runnable runnable=new Runnable(){
                    public void run(){
                        deleted.setVisibility(View.INVISIBLE);
                    }
                };
                if(taskId != -1) {
                    //find specific record in the database
                    db.delete("task", "id =?", new String[]{ taskId.toString()});
                    deleted=(TextView)findViewById(R.id.delete_successful);
                    deleted.setVisibility(View.VISIBLE);
                    Handler handler=new Handler();
                    handler.postDelayed(runnable,1000);
                }
                else{ //does not find this record in the database

                }
                Intent intent=new Intent(each_tsk_detail.this,DetailList.class);
                intent.putExtra("module_code",mod_code);
                startActivity(intent);
            }
        });
    }
}

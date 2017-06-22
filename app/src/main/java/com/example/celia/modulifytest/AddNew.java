package com.example.celia.modulifytest;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.RunnableFuture;

import static android.content.ContentValues.TAG;


public class AddNew extends Fragment {
    private View v;
    private TextView added,invalid;
    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private ContentValues values;
    private String mod_code="mod",task="task",level="easy",day_of_week="";
    private int DD=0,MM=0,YYYY=0;
    private int diff_pt=1;
    private Spinner date;
    private static final String[] options={"select","Monday","Tuesday","Wednesday","Thursday","Friday"};
    LinearLayout view,view2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_add_new, container, false);
        dbHelper=new MyDatabaseHelper(getActivity(),"TasksStore.db",null,1);
        Log.d(TAG, "onCreateView: ");
        Button add=(Button)v.findViewById(R.id.add);
        Button cancel=(Button)v.findViewById(R.id.cancel);
        add.setOnClickListener(addListener);
        cancel.setOnClickListener(deleteListener);
        added = (TextView) v.findViewById(R.id.added);
        invalid=(TextView)v.findViewById(R.id.invalid_input);
        add_dates();

        view=(LinearLayout)v.findViewById(R.id.tut_date);
        view2=(LinearLayout)v.findViewById(R.id.ass_date);
        view.setVisibility(View.INVISIBLE);
        view2.setVisibility(View.INVISIBLE);

        RadioGroup task_type=(RadioGroup) v.findViewById(R.id.task_select);
        task_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton tut=(RadioButton)v.findViewById(R.id.Tutorial);
                if(tut.isChecked()){
                    LinearLayout tut_view=(LinearLayout)v.findViewById(R.id.tut_date);
                    view.setVisibility(View.VISIBLE);
                    view2.setVisibility(View.INVISIBLE);
                }
                else{
                    view2.setVisibility(View.VISIBLE);
                    view.setVisibility(View.INVISIBLE);
                }
            }
        });
        return v;
    }
    private void add_dates(){
        date=(Spinner)v.findViewById(R.id.day_of_week);
        ArrayList<String> days=new ArrayList<>();
        for(int i=0;i<options.length;i++) days.add(options[i]);
        ArrayAdapter<String> adapterDate=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,days);
        adapterDate.setDropDownViewResource(android.R.layout.simple_spinner_item);
        date.setAdapter(adapterDate);
    }

    Runnable runnable=new Runnable(){
        public void run(){
            added.setVisibility(View.INVISIBLE);
        }
    };
    Runnable runnable1=new Runnable() {
        @Override
        public void run() {
            invalid.setVisibility(View.INVISIBLE);
        }
    };
    private Button.OnClickListener addListener=new Button.OnClickListener() {
        public void onClick(View view) {
            CreateNewTask(true);
        }
    };
    private Button.OnClickListener deleteListener=new Button.OnClickListener(){
      public void onClick(View view){
          CreateNewTask(false);
          }
    };
    private void CreateNewTask(boolean add){
        Boolean isValid=true;

        EditText module_code=(EditText)v.findViewById(R.id.enter_mod_code);
        mod_code = module_code.getText().toString();
        if(mod_code.equals("")) isValid=false;

        RadioGroup task_type=(RadioGroup) v.findViewById(R.id.task_select);
        RadioButton tut=(RadioButton)v.findViewById(R.id.Tutorial);
        RadioButton ass=(RadioButton)v.findViewById(R.id.Assignment);
        RadioButton lab=(RadioButton)v.findViewById(R.id.Lab);
        if(tut.isChecked()||ass.isChecked()||lab.isChecked()){
            RadioButton selected_task = (RadioButton) v.findViewById(task_type.getCheckedRadioButtonId());
            task = selected_task.getText().toString();
        }
        else isValid=false;

        EditText day = (EditText) v.findViewById(R.id.day);
        EditText month = (EditText) v.findViewById(R.id.month);
        EditText year = (EditText) v.findViewById(R.id.year);
        if(tut.isChecked()){
            day_of_week=date.getSelectedItem().toString();
            String[] ops={"select","Monday","Tuesday","Wednesday","Thursday","Friday"};
            for(int i=0;i<ops.length;i++)
                if(day_of_week.equals(ops[i])){
                    DD=i;
                    break;
                }
        }
        else{
            if (day.getText().toString().equals("")) isValid = false;
            else DD = Integer.parseInt(day.getText().toString());
            if (month.getText().toString().equals("")) isValid = false;
            else MM = Integer.parseInt(month.getText().toString());
            if (year.getText().toString().equals("")) isValid = false;
            else YYYY = Integer.parseInt(year.getText().toString());
        }

        RadioGroup diff_level=(RadioGroup)v.findViewById(R.id.select_diff_level);
        RadioButton easy=(RadioButton)v.findViewById(R.id.easy);
        RadioButton medium=(RadioButton)v.findViewById(R.id.medium);
        RadioButton hard=(RadioButton)v.findViewById(R.id.hard);
        if(easy.isChecked()||medium.isChecked()||hard.isChecked()) {
            RadioButton selected_level = (RadioButton) v.findViewById(diff_level.getCheckedRadioButtonId());
            if (add && diff_level.isSelected()) {
                level = selected_level.getText().toString();
                if (level.equals("easy")) diff_pt = 5;
                else if (level.equals("medium")) diff_pt = 3;
                else diff_pt = 1;
            }
        }
        else isValid=false;

        module_code.setText(null);
        task_type.clearCheck();
        day.setText(null);
        month.setText(null);
        year.setText(null);
        diff_level.clearCheck();
        view.setVisibility(View.INVISIBLE);
        view2.setVisibility(View.INVISIBLE);
        date.setSelection(0);

        if(add&&isValid){
            addToDataBase();
            added.setVisibility(View.VISIBLE);
            Handler handler=new Handler();
            handler.postDelayed(runnable,1000);
        }
        else if(add&&!isValid){
            invalid.setVisibility(View.VISIBLE);
            Handler handler=new Handler();
            handler.postDelayed(runnable1,1000);
        }
    }
    private void addToDataBase(){
        db=dbHelper.getWritableDatabase();
        values=new ContentValues();
        values.put("code",mod_code);
        values.put("name",task);
        values.put("deadline_dd",DD);
        values.put("deadline_mm",MM);
        values.put("deadline_yyyy",YYYY);
        values.put("difficulty",diff_pt);
        db.insert("task",null,values);
    }


}

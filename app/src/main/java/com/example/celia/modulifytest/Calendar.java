package com.example.celia.modulifytest;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Calendar extends Fragment {
    private String[] months={"January","February","March","April","May","June","July","August","September","October","November","December"};
    private String[] ops={"select","Monday","Tuesday","Wednesday","Thursday","Friday"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_calendar, container, false);
        TextView calendar=(TextView)v.findViewById(R.id.calendar_date);
        calendar.setText(today());
        return v;
    }
    private String today(){
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = dateFormat.format(now);
        String month=months[Integer.parseInt(today.substring(5,7))];
        return month+" "+today.substring(8,10)+", "+today.substring(0,4);
    }

}

package com.example.celia.modulifytest;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateCalculate {
    int DD,MM,YYYY;
    final Calendar c = Calendar.getInstance();
    public DateCalculate(int DD,int MM,int YYYY){
        this.DD=DD;
        this.MM=MM;
        this.YYYY=YYYY;
    }
    public String getDay(){
        int day_of_week = Integer.parseInt(String.valueOf(c.get(Calendar.DAY_OF_WEEK)));
        if(day_of_week==1) day_of_week=6;
        else day_of_week=day_of_week-1;
        if(MM==0&&YYYY==0){
            if(day_of_week<=DD) return Integer.toString(DD-day_of_week);
            else return Integer.toString(7-day_of_week+DD);
        }
        else{
            String ddl = Integer.toString(YYYY) + "-" + Integer.toString(MM) + "-" + Integer.toString(DD);
            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String today = dateFormat.format(now);

            long day = 0;
            try {
                java.util.Date DDL = dateFormat.parse(ddl);
                java.util.Date TODAY = dateFormat.parse(today);
                day = (DDL.getTime() - TODAY.getTime()) / (24 * 60 * 60 * 1000);
            } catch (Exception e) {
                return "";
            }
            return Long.toString(day);
        }
    }
}

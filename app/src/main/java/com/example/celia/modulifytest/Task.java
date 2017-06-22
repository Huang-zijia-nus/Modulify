package com.example.celia.modulifytest;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Task {
    private String mod;
    private String type;
    private int DiffLevel;
    private int DD;
    private int MM;
    private int YY;
    final Calendar c = Calendar.getInstance();
    private String[] months={"January","February","March","April","May","June","July","August","September","October","November","December"};
    public Task(String mod,String type,int DiffLevel,int DD,int MM,int YY){
        this.mod=mod;
        this.type=type;
        this.DiffLevel=DiffLevel;
        this.DD=DD;
        this.YY=YY;
        this.MM=MM;
    }
    public String getMod(){
        return mod;
    }
    public String getType(){ return type; }
    public String getDay(){
        int day_of_week = Integer.parseInt(String.valueOf(c.get(Calendar.DAY_OF_WEEK)));
        if(day_of_week==1) day_of_week=6;
        else day_of_week=day_of_week-1;
        if(MM==0&&YY==0){
            if(day_of_week<=DD) return Integer.toString(DD-day_of_week);
            else return Integer.toString(7-day_of_week+DD);
        }
        else{
            String ddl = Integer.toString(YY) + "-" + Integer.toString(MM) + "-" + Integer.toString(DD);
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
    public String getDiffLevel(){ return Integer.toString(DiffLevel); }
    public String getDD(){return Integer.toString(DD);}
    public String getMM(){return Integer.toString(MM);}
    public String getYY(){return Integer.toString(YY);}
    public String getDate(){
        String month=months[MM-1];
        return month+" "+DD+", "+YY;
    }
}

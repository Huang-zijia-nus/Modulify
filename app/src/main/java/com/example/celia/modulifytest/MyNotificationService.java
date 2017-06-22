package com.example.celia.modulifytest;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyNotificationService extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private MyDatabaseHelper dbHelper;
    MyNotificationService this1;
    public MyNotificationService() {
        this1 = this;
    }


    @Override
    public void onCreate(){
        super.onCreate();
        Log.d("MyNotificationService","onCreate executed");
    }

    @Override
    public int onStartCommand(Intent intent, int flag, int startId){
        Log.d("MyNotificationService","onStartCommand executed");
        new Thread(new Runnable() {
            @Override
            public void run(){
                Intent intent = new Intent(this1, CountDown.class);
                PendingIntent pi = PendingIntent.getActivity(this1, 0, intent, 0);

                ArrayList<String> list_tomorrow = new ArrayList<>();

                dbHelper = new MyDatabaseHelper(this1, "TasksStore.db", null, 1);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("task", null, null, null, null, null, null);
                if(cursor.moveToFirst()) {
                    do {
                        int dd = cursor.getInt(cursor.getColumnIndex("deadline_dd"));
                        int mm = cursor.getInt(cursor.getColumnIndex("deadline_mm"));
                        int yyyy = cursor.getInt(cursor.getColumnIndex("deadline_yyyy"));
                        DateCalculate date=new DateCalculate(dd,mm,yyyy);
                        String daysLeft=date.getDay();
                        if(daysLeft.equals("1")){
                            String mod_code = cursor.getString(cursor.getColumnIndex("code"));
                            String tsk_type = cursor.getString(cursor.getColumnIndex("name"));
                            list_tomorrow.add(mod_code+" "+tsk_type);
                        }
                    } while (cursor.moveToNext());
                }
                if(list_tomorrow.size() > 0) {
                    String str = "You have "+list_tomorrow.toString()+" deadlines tomorrow!";
                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    Notification notification = new NotificationCompat.Builder(MyNotificationService.this)
                            .setContentTitle("deadlines tomorrow")
                            .setContentText(str)
                            .setWhen(System.currentTimeMillis())
                            .setContentIntent(pi)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .build();
                    manager.notify(1, notification);
                }
            }
        }).start();

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long period = 6*60*60*1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + period;
        Intent i = new Intent(this, MyNotificationService.class);
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flag, startId);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("MyAlarmService", "onDestroy executed");
    }

}

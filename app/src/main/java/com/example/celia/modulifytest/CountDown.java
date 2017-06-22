package com.example.celia.modulifytest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;

public class CountDown extends AppCompatActivity {
    private NoSlidingViewPager viewPager;
    private MenuItem menuItem;
    private ArrayList<Fragment> fragments;
    private Adapter adapter;
    private BottomNavigationView.OnNavigationItemSelectedListener mListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.Important:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.module_list:
                    viewPager.setCurrentItem(1);
                    return true;
                /*case R.id.calendar:
                    viewPager.setCurrentItem(2);
                    return true;*/
                case R.id.add_new:
                    viewPager.setCurrentItem(3);
                    return true;
            }
            return false;
        }

    };

   private MyDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_count_down);
        viewPager = (NoSlidingViewPager) findViewById(R.id.vp_main_container);
        fragments=new ArrayList<>(3);
        fragments.add(new Important());
        fragments.add(new ModuleList());
        fragments.add(new Calendar());
        fragments.add(new AddNew());

        adapter=new Adapter(getSupportFragmentManager(),fragments);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mListener);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);

        Intent serviceIntent = new Intent(this, MyNotificationService.class);
        startService(serviceIntent);

        Intent fromDetail=getIntent();
        if(fromDetail.hasExtra("reminder")){
            viewPager.setCurrentItem(1);
            navigation.setSelectedItemId(R.id.module_list);
        }
    }

}

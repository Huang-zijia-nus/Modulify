package com.example.celia.modulifytest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

public class Adapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> fragments;
    private FragmentManager fm;
    public Adapter(FragmentManager fm,ArrayList<Fragment> fragments){
        super(fm);
        this.fm=fm;
        this.fragments=fragments;
    }
    @Override
    public Fragment getItem(int position){
        return fragments.get(position);
    }
    @Override
    public int getCount(){ return fragments.size();}
    public int getItemPosition(Object object){
        return PagerAdapter.POSITION_NONE;
    }
}

package com.example.celia.modulifytest;

import java.util.Comparator;


public class tsk_compare implements Comparator<Task> {
    public int compare(Task o1,Task o2) {
        int urgent_level1=Integer.parseInt(o1.getDiffLevel())+Integer.parseInt(o1.getDay());
        int urgent_level2=Integer.parseInt(o2.getDiffLevel())+Integer.parseInt(o1.getDay());
        return urgent_level2-urgent_level1;
    }
}

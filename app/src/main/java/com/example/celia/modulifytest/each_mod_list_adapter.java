package com.example.celia.modulifytest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class each_mod_list_adapter extends ArrayAdapter<Task> {
    private List<Task> data;
    private Context context;
    private int resourceId;
    private String[] ops={"select","Monday","Tuesday","Wednesday","Thursday","Friday"};
    public each_mod_list_adapter(Context context,int textViewResourceId,List<Task> data){
        super(context,textViewResourceId,data);
        this.context=context;
        this.resourceId=textViewResourceId;
        this.data=data;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder=null;
        if (null == convertView)
        {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(R.layout.in_module_list_each_task, null);
            viewHolder.tsk=(TextView) convertView.findViewById(R.id.in_module_list_each_tsk_type);
            viewHolder.date=(TextView) convertView.findViewById(R.id.in_module_list_each_tsk_date);
            viewHolder.day=(TextView)convertView.findViewById(R.id.in_module_list_each_tsk_days_left);
            convertView.setTag(viewHolder);
        }
        else viewHolder = (ViewHolder) convertView.getTag();
        Task tsk=data.get(position);
        if (null != tsk)
        {
            viewHolder.tsk.setText(tsk.getType());
            viewHolder.day.setText(tsk.getDay());
            if(tsk.getMM().equals("0")&&tsk.getYY().equals("0")) viewHolder.date.setText(ops[Integer.parseInt(tsk.getDD())]);
            else viewHolder.date.setText(tsk.getDate());
        }
        return convertView;
    }
    class ViewHolder{
        TextView tsk;
        TextView day;
        TextView date;
    }
}

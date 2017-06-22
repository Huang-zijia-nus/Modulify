package com.example.celia.modulifytest;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class count_list_adapter extends ArrayAdapter<Task> {
    private List<Task> data;
    private Context context;
    private int resourceId;
    private static final String[] ops={"select","Monday","Tuesday","Wednesday","Thursday","Friday"};
    public count_list_adapter(Context context, int textViewResourceId,List<Task> data){
        super(context,textViewResourceId,data);
        this.resourceId=textViewResourceId;
        this.data=data;
        this.context=context;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (null == convertView)
        {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(R.layout.each_task, null);
            viewHolder.code=(TextView) convertView.findViewById(R.id.tsk_code);
            viewHolder.date=(TextView) convertView.findViewById(R.id.tsk_date);
            viewHolder.daysLeft=(TextView) convertView.findViewById(R.id.tsk_days_left);

            convertView.setTag(viewHolder);
        }
        else viewHolder = (ViewHolder) convertView.getTag();
        Task tsk=getItem(position);
        if (null != tsk)
        {
            viewHolder.code.setText(tsk.getMod()+"   "+tsk.getType());
            if(tsk.getMM().equals("0")&&tsk.getYY().equals("0")) viewHolder.date.setText(ops[Integer.parseInt(tsk.getDD())]);
            else viewHolder.date.setText(tsk.getDate());
            viewHolder.daysLeft.setText(tsk.getDay());
        }
        return convertView;
    }
    class ViewHolder{
        TextView code;
        TextView date;
        TextView daysLeft;

    }
}

package com.everystepcounts.akshay.everystepcounts2;

import android.content.Context;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class CustomAdapter extends BaseAdapter {
    Context c;
    ArrayList<Running> record;


    public CustomAdapter(Context c, ArrayList<Running> record) {
        this.c = c;
        this.record = record;
    }



    @Override
    public int getCount() {
        return record.size();
    }

    @Override
    public Object getItem(int position) {
        return record.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int i=position;
        LayoutInflater inflater=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView=inflater.inflate(R.layout.row_item, null,true);
        TextView distance = (TextView) rowView.findViewById(R.id.distance);
        TextView time = (TextView) rowView.findViewById(R.id.time);
        TextView avgPace = (TextView) rowView.findViewById(R.id.avgPace);


        distance.setText(record.get(position).distance);
        time.setText(record.get(position).time);
        if(Float.parseFloat(record.get(position).time)!=0.0) {
            float avg = Float.parseFloat(record.get(position).distance) / Float.parseFloat(record.get(position).time);
            avgPace.setText(Float.toString(avg));
        }
        else
            avgPace.setText("0");
        return  rowView;

    }
}

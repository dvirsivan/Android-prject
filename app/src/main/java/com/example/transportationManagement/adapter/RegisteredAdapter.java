package com.example.transportationManagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.transportationManagement.Entities.Travel;
import com.example.transportationManagement.Model.RegisteredItem;
import com.example.transportationManagement.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RegisteredAdapter extends BaseAdapter {
    private Context context;
    private List<RegisteredItem> items;

    public RegisteredAdapter(Context context, List<RegisteredItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_registered, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Travel.RequestType[] enumL;
        enumL = new Travel.RequestType[]{Travel.RequestType.sent, Travel.RequestType.accepted, Travel.RequestType.run,
                Travel.RequestType.close};
        RegisteredItem currentItem = (RegisteredItem) getItem(position);
        viewHolder.source.setText(currentItem.getSource());
        viewHolder.date.setText(currentItem.getDate());
        viewHolder.company.setAdapter(new ArrayAdapter(this.context,R.layout.fragment_registered,
                currentItem.getCompany()));
        viewHolder.destinations.setAdapter(new ArrayAdapter(this.context,R.layout.fragment_registered,
                currentItem.getDestinations()));
        viewHolder.statuses.setAdapter(new ArrayAdapter(this.context,R.layout.fragment_registered,enumL));

        return convertView;
    }

    private class ViewHolder {
        TextView source;
        TextView date;
        Spinner destinations;
        Spinner statuses;
        Spinner company;

        public ViewHolder(View view) {
            this.source =  source;
            this.date = date;
            this.destinations = destinations;
            this.statuses = statuses;
            this.company = company;
        }
    }
}

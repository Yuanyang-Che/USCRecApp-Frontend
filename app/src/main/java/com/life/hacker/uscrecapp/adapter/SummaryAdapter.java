package com.life.hacker.uscrecapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.life.hacker.uscrecapp.R;
import com.life.hacker.uscrecapp.activity.MapsActivity;
import com.life.hacker.uscrecapp.model.Timeslot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SummaryAdapter extends ArrayAdapter<Timeslot> {
    private Context mContext;
    private int mResource;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        Button btn;
        TextView date;
        TextView timeslot;
    }

    /**
     * Default constructor for the PersonListAdapter
     *
     * @param context
     * @param resource
     * @param objects
     */
    public SummaryAdapter(Context context, int resource, List<Timeslot> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the timeslot information
//        Day day = getItem(position).getDay();
//        Date date = day.getDate();
        String pattern = "MM/dd/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        Date today = Calendar.getInstance().getTime();
        String todayAsString = df.format(today);

        int timeindex = getItem(position).getTimeIndex();
        boolean isPast = getItem(position).isPast();
        // ------------------------------------------------------

        //create the view result for showing the animation
        View result;
        //ViewHolder object
        SummaryAdapter.ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new SummaryAdapter.ViewHolder();
            holder.btn = (Button) convertView.findViewById(R.id.book);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.timeslot = (TextView) convertView.findViewById(R.id.timeslot);

//            result = convertView;

            convertView.setTag(holder);
            result = convertView;
        } else {
            holder = (SummaryAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }
        if (isPast) {
            holder.date.setText(todayAsString);
            holder.timeslot.setText(Integer.toString(timeindex));
            holder.btn.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.grey, getDropDownViewTheme()));
            holder.btn.setText("Past Appointment");
            holder.btn.setOnClickListener(view -> {});
        } else {
            holder.btn.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.light_blue, getDropDownViewTheme()));
            holder.btn.setText("Cancel");
            holder.btn.setOnClickListener(view -> {
                mContext.startActivity(new Intent(mContext, MapsActivity.class));
            });
        }


        return result;
    }
}

package com.life.hacker.uscrecapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.life.hacker.uscrecapp.R;
import com.life.hacker.uscrecapp.SessionData;
import com.life.hacker.uscrecapp.Util;
import com.life.hacker.uscrecapp.activity.MapsActivity;
import com.life.hacker.uscrecapp.model.Timeslot;
import com.life.hacker.uscrecapp.network.MessageCenter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        TextView center;
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
        String pattern = "MM/dd/yyyy";
        DateFormat df = new SimpleDateFormat(pattern, Locale.US);
        Date date = getItem(position).getDay().getDate();
        String todayAsString = df.format(date);

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
            holder.btn = convertView.findViewById(R.id.book);
            holder.date = convertView.findViewById(R.id.date);
            holder.timeslot = convertView.findViewById(R.id.timeslot);
            holder.center = convertView.findViewById(R.id.center);
            convertView.setTag(holder);
        } else {
            holder = (SummaryAdapter.ViewHolder) convertView.getTag();
        }
        result = convertView;

        holder.center.setText(getItem(position).getDay().getCenter().getName());
        if (isPast) {
            holder.date.setText(todayAsString);

            //holder.timeslot.setText(Integer.toString(timeindex < 12 ? timeindex : timeindex - 12) + (timeindex > 12 ? " PM" : " AM"));
            holder.timeslot.setText(Util.convertTimeIdx(timeindex));
            holder.btn.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.grey, getDropDownViewTheme()));
            holder.btn.setText("Past Appointment");
            holder.btn.setOnClickListener(view -> {});
        } else {
            holder.btn.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.light_blue, getDropDownViewTheme()));
            holder.btn.setText("Cancel");
            holder.btn.setOnClickListener(view -> {
                //TODO
                String loc = getItem(position).getDay().getCenter().getName();
                String dateStr = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(getItem(position).getDay().getDate());
                String timeIdxStr = Integer.toString(getItem(position).getTimeIndex());

                MessageCenter.getInstance().CancelRequest(loc, dateStr, timeIdxStr, SessionData.getInstance().getToken(), mContext);
                //mContext.startActivity(new Intent(mContext, MapsActivity.class));
            });
        }

        return result;
    }
}

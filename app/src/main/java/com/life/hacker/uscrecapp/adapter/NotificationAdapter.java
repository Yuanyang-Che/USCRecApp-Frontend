package com.life.hacker.uscrecapp.adapter;

import android.content.Context;
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
import com.life.hacker.uscrecapp.model.Timeslot;
import com.life.hacker.uscrecapp.network.MessageCenter;

import java.util.Date;
import java.util.List;

public class NotificationAdapter extends ArrayAdapter<Timeslot> {
    private Context mContext;
    private int mResource;


    private static class ViewHolder {
        Button bookBtn, cancelBtn;
        TextView date;
        TextView timeslot;
        TextView center;
    }

    public NotificationAdapter(Context context, int resource, List<Timeslot> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Date date = getItem(position).getDay().getDate();
        String dateString = Util.formatDateToStardard(date);

        int timeindex = getItem(position).getTimeIndex();
        boolean isPast = getItem(position).isPast();
        // ------------------------------------------------------

        //create the view result for showing the animation
        View result;
        //ViewHolder object
        NotificationAdapter.ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new NotificationAdapter.ViewHolder();
            holder.bookBtn = convertView.findViewById(R.id.timeSlotAdapterBookBtn);
            holder.date = convertView.findViewById(R.id.timeslotAdapterDateTv);
            holder.timeslot = convertView.findViewById(R.id.timeslotAdapterTimeIdxTv);
            holder.center = convertView.findViewById(R.id.timeslotAdapterCenterTv);
            convertView.setTag(holder);
        } else {
            holder = (NotificationAdapter.ViewHolder) convertView.getTag();
        }
        result = convertView;

        holder.date.setText(dateString);
        holder.center.setText(getItem(position).getDay().getCenter().getName());

        holder.timeslot.setText(Util.formatTimeIndex(timeindex));
        if (isPast) {
            //holder.timeslot.setText(Integer.toString(timeindex < 12 ? timeindex : timeindex - 12) + (timeindex > 12 ? " PM" : " AM"));

            holder.bookBtn.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.grey, getDropDownViewTheme()));
            holder.bookBtn.setText("Past Appointment");
            holder.bookBtn.setOnClickListener(view -> {});
        } else {
            holder.bookBtn.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.light_blue, getDropDownViewTheme()));
            holder.bookBtn.setText("Cancel");
            holder.bookBtn.setOnClickListener(view -> {
                //TODO
                String loc = getItem(position).getDay().getCenter().getName();
                String dateStr = Util.formatDateToStardard(getItem(position).getDay().getDate());
                int timeIdx = getItem(position).getTimeIndex();

                String timeIdxStr = Util.formatTimeIndex(timeIdx);

                MessageCenter.getInstance().CancelBookRequest(loc, dateStr, timeIdxStr, SessionData.getInstance().getToken(), mContext);
                //mContext.startActivity(new Intent(mContext, MapsActivity.class));
            });
        }

        return result;
    }
}


package com.life.hacker.uscrecapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.life.hacker.uscrecapp.Notification.NotificationEntry;
import com.life.hacker.uscrecapp.R;
import com.life.hacker.uscrecapp.SessionData;
import com.life.hacker.uscrecapp.Util;
import com.life.hacker.uscrecapp.activity.ConfirmNotificationBookFragment;
import com.life.hacker.uscrecapp.activity.NotificationCenterActivity;
import com.life.hacker.uscrecapp.model.Day;
import com.life.hacker.uscrecapp.model.Timeslot;
import com.life.hacker.uscrecapp.network.MessageCenter;

import java.util.Date;
import java.util.List;

public class NotificationAdapter extends ArrayAdapter<NotificationEntry> {
    private NotificationCenterActivity mContext;
    private int mResource;

    private static class ViewHolder {
        Button bookBtn, cancelBtn;
        TextView date;
        TextView timeslot;
        TextView center;
    }

    public NotificationAdapter(NotificationCenterActivity context, int resource, List<NotificationEntry> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Date date = getItem(position).getD();
        String dateString = Util.formatDateToStandard(date);

        int timeindex = getItem(position).getTimeIndex();
        // ------------------------------------------------------

        //create the view result for showing the animation
        View result;
        //ViewHolder object
        NotificationAdapter.ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new NotificationAdapter.ViewHolder();
            holder.bookBtn = convertView.findViewById(R.id.waitlistAdapterBookBtn);
            holder.cancelBtn = convertView.findViewById(R.id.waitlistAdapterCancelBtn);
            holder.date = convertView.findViewById(R.id.waitlistAdapterDateTv);
            holder.timeslot = convertView.findViewById(R.id.waitlistAdapterTimeIdxTv);
            holder.center = convertView.findViewById(R.id.waitlistAdapterCenterTv);
            convertView.setTag(holder);
        } else {
            holder = (NotificationAdapter.ViewHolder) convertView.getTag();
        }
        result = convertView;

        holder.date.setText(dateString);
        holder.center.setText(getItem(position).getCenterName());
        holder.timeslot.setText(Util.formatTimeIndex(timeindex));

        holder.bookBtn.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.holo_green_dark, getDropDownViewTheme()));
        holder.bookBtn.setText("Book");
        holder.bookBtn.setOnClickListener(view -> {
            FragmentActivity fa = (FragmentActivity) mContext;
            DialogFragment frag = new ConfirmNotificationBookFragment(new Timeslot(timeindex, 2, 0,
                    new Day(date, null, null), false, false, false),
                    getItem(position).getCenterName(), mContext);
            frag.show(fa.getSupportFragmentManager(), "confirm");
        });
        holder.cancelBtn.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.light_blue, getDropDownViewTheme()));
        holder.cancelBtn.setText("cancel");
        holder.cancelBtn.setOnClickListener(view -> {
            // CancelWaitlistRequest(String center_name, String date, String timeslot, String user_token, Context context)
            MessageCenter.getInstance().CancelWaitlistRequest(getItem(position).getCenterName(),
                    Util.formatDateToStandard(date), Util.formatTimeIndex(timeindex),
                    SessionData.getInstance().getToken(), mContext);
            mContext.removeTimeslot(timeindex, date, getItem(position).getCenterName());
        });

        return result;
    }
}


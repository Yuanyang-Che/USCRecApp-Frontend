package com.life.hacker.uscrecapp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.life.hacker.uscrecapp.R;
import com.life.hacker.uscrecapp.Util;
import com.life.hacker.uscrecapp.activity.BookingActivity;
import com.life.hacker.uscrecapp.activity.ConfirmBookFragment;
import com.life.hacker.uscrecapp.activity.ConfirmWaitListFragment;
import com.life.hacker.uscrecapp.model.Timeslot;

import java.util.Date;
import java.util.List;

public class TimeslotListAdapter extends ArrayAdapter<Timeslot> {

    private Context mContext;
    private int mResource;
    private Dialog mDialog;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        Button btn;
        TextView date;
        TextView centerName;
        TextView timeslot;
    }

    /**
     * Default constructor for the TimeslotListAdapter
     *
     * @param context
     * @param resource
     * @param objects
     */
    public TimeslotListAdapter(Context context, int resource, List<Timeslot> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        mDialog = new Dialog(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the timeslot information
        Date today = getItem(position).getDay().getDate();
        String todayAsString = Util.formatDateToStandard(today);

        int timeindex = getItem(position).getTimeIndex();
        boolean isBookable = getItem(position).isBookable();
        boolean isBooked = getItem(position).isBooked();
        boolean isWaitListed = getItem(position).isWaitListed();
        // ------------------------------------------------------

        //create the view result for showing the animation
        View result;
        //ViewHolder object
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.btn = convertView.findViewById(R.id.timeSlotAdapterButtonBook);
            holder.date = convertView.findViewById(R.id.timeslotAdapterTextViewDate);
            holder.timeslot = convertView.findViewById(R.id.timeslotAdapterTextViewTimeIndex);
            holder.centerName = convertView.findViewById(R.id.timeslotAdapterTextViewCenter);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        result = convertView;

        holder.date.setText(todayAsString);
        holder.timeslot.setText(Util.convertTimeIndexToHour(timeindex));
        BookingActivity ba = (BookingActivity) mContext;
        holder.centerName.setText(ba.getCenterName());

        if (isBooked) {
            holder.btn.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.grey, getDropDownViewTheme()));
            holder.btn.setText(mContext.getResources().getText(R.string.bookingButtonBooked));

            holder.btn.setOnClickListener(view -> { });

            return result;
        }

        if (isBookable) {
            //user waitlisted for this and this slot is free
            if (isWaitListed) {
                holder.btn.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.light_blue, getDropDownViewTheme()));
                holder.btn.setText(mContext.getResources().getText(R.string.bookingButtonBookAndWaitlisted));
                //user didn't waitlist for this and this is free
            } else {
                holder.btn.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.holo_green_dark, getDropDownViewTheme()));
                holder.btn.setText(mContext.getResources().getText(R.string.bookingButtonBook));
            }
            holder.btn.setOnClickListener(view -> {
                FragmentActivity fa = (FragmentActivity) mContext;
                DialogFragment frag = new ConfirmBookFragment(getItem(position), ba.getCenterName(), mContext);
                frag.show(fa.getSupportFragmentManager(), "confirm");
            });
            return result;
        }

        //Not bookable, but user waitlisted
        if (isWaitListed) {
            holder.btn.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.grey, getDropDownViewTheme()));
            holder.btn.setText(mContext.getResources().getText(R.string.bookingButtonWaitlisted));
            holder.btn.setOnClickListener(view -> { });
            return result;
        }

        //Not Waitlisted nor bookable
        holder.btn.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.purple_200, getDropDownViewTheme()));
        holder.btn.setText(mContext.getResources().getText(R.string.bookingButtonWaitlist));
        holder.btn.setOnClickListener(view -> {
            FragmentActivity fa = (FragmentActivity) mContext;
            DialogFragment frag = new ConfirmWaitListFragment(getItem(position), ba.getCenterName(), mContext);
            frag.show(fa.getSupportFragmentManager(), "confirm");
        });

        return result;
    }
}

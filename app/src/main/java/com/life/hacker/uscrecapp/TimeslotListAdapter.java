package com.life.hacker.uscrecapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.life.hacker.uscrecapp.Model.Day;
import com.life.hacker.uscrecapp.Model.Timeslot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TimeslotListAdapter extends ArrayAdapter<Timeslot> {

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
     * @param context
     * @param resource
     * @param objects
     */
    public TimeslotListAdapter(Context context, int resource, ArrayList<Timeslot> objects) {
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
        boolean isBookable = getItem(position).isBookable();
        // ------------------------------------------------------

//        String name = getItem(position).getName();
//        String birthday = getItem(position).getBirthday();
//        String sex = getItem(position).getSex();
//
//        //Create the person object with the information
//        Person person = new Person(name,birthday,sex);

        //create the view result for showing the animation
        View result;
        //ViewHolder object
        ViewHolder holder;

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.btn = (Button) convertView.findViewById(R.id.book);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.timeslot = (TextView) convertView.findViewById(R.id.timeslot);
//            holder.name = (TextView) convertView.findViewById(R.id.textView1);
//            holder.birthday = (TextView) convertView.findViewById(R.id.textView2);
//            holder.sex = (TextView) convertView.findViewById(R.id.textView3);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


//        Animation animation = AnimationUtils.loadAnimation(mContext,
//                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
//        result.startAnimation(animation);
//        lastPosition = position;

        holder.date.setText(todayAsString);
        holder.timeslot.setText(Integer.toString(timeindex));
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, MapsActivity.class));
            }
        });

//        holder.name.setText(person.getName());
//        holder.birthday.setText(person.getBirthday());
//        holder.sex.setText(person.getSex());


        return result;
    }
}

package com.life.hacker.uscrecapp.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.life.hacker.uscrecapp.SessionData;
import com.life.hacker.uscrecapp.Util;
import com.life.hacker.uscrecapp.model.Timeslot;
import com.life.hacker.uscrecapp.network.MessageCenter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ConfirmActionFragment extends DialogFragment {

    Timeslot t;
    String centerName;
    Context mContext;

    public ConfirmActionFragment(Timeslot t, String cn, Context mContext) {
        super();
        this.t = t;
        centerName = cn;
        this.mContext = mContext;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure you want to Book this time?")
                .setPositiveButton("Confirm", (dialog, id) -> {
                    // String center_name, String date, String timeslot, String user_token, Context context
                    // yyyy-mm-dd, 08:00:00
                    Date today = t.getDay().getDate();
                    String dayString = Util.formatDateToStardard(today);

                    int time = t.getTimeIndex();
                    String timeStr = Util.formatTimeIndex(time);
                    MessageCenter.getInstance().BookRequest(centerName, dayString, Util.formatTimeIndex(time),
                            SessionData.getInstance().getToken(), mContext);

                })
                .setNegativeButton("Cancel", (dialog, id) -> {
                    // User cancelled the dialog
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}

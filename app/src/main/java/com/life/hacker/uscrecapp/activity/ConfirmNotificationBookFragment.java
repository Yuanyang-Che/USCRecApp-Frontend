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

import java.util.Date;

public class ConfirmNotificationBookFragment extends DialogFragment {

    Timeslot t;
    String centerName;
    NotificationCenterActivity mContext;

    public ConfirmNotificationBookFragment(Timeslot t, String cn, NotificationCenterActivity mContext) {
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
                    Date today = t.getDay().getDate();
                    String dayString = Util.formatDateToStardard(today);

                    int time = t.getTimeIndex();
                    MessageCenter.getInstance().NotificationBookRequest(centerName, dayString, Util.formatTimeIndex(time),
                            SessionData.getInstance().getToken(), mContext);

                    mContext.removeTimslot(time, today, centerName);
                })
                .setNegativeButton("Cancel", (dialog, id) -> {
                    // User cancelled the dialog
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}

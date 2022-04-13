package com.life.hacker.uscrecapp.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.life.hacker.uscrecapp.R;
import com.life.hacker.uscrecapp.SessionData;
import com.life.hacker.uscrecapp.Util;
import com.life.hacker.uscrecapp.model.Timeslot;
import com.life.hacker.uscrecapp.network.MessageCenter;

import java.util.Date;

public class ConfirmBookFragment extends DialogFragment {

    Timeslot t;
    String centerName;
    Context mContext;

    public ConfirmBookFragment(Timeslot t, String cn, Context mContext) {
        super();
        this.t = t;
        centerName = cn;
        this.mContext = mContext;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getResources().getString(R.string.confirmBookingMessage))
                .setPositiveButton("Confirm", (dialog, id) -> {
                    Date today = t.getDay().getDate();
                    String dayString = Util.formatDateToStandard(today);

                    int time = t.getTimeIndex();
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

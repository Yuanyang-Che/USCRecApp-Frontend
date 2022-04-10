package com.life.hacker.uscrecapp.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.life.hacker.uscrecapp.R;
import com.life.hacker.uscrecapp.Util;
import com.life.hacker.uscrecapp.adapter.TimeslotListAdapter;
import com.life.hacker.uscrecapp.model.Timeslot;
import com.life.hacker.uscrecapp.network.MessageCenter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BookingActivity extends FragmentActivity {

    private Button backtoMapButton;
    private Button prevDayButton, nextDayButton;
    private TextView dateText;

    private Date currentDate;

    public Date getCurrentDate() { return currentDate; }

    private String centerName;

    public String getCenterName() {
        return centerName;
    }

    public void takeToastMessage(String message) {
        runOnUiThread(() -> Util.takeToastMessage(getApplicationContext(), message));
    }

    public void jumpBackToMap(String message) {
        Intent i = new Intent(BookingActivity.this, MapsActivity.class);
        i.putExtra("Message", message);
        startActivity(i);
    }

    public void setTimeSlotList(List<Timeslot> timeSlotList) {
        ListView mListView = findViewById(R.id.bookingListView);
        TimeslotListAdapter adapter = new TimeslotListAdapter(this, R.layout.timeslot_adapter, timeSlotList);
        mListView.setAdapter(adapter);
    }

    public void refreshPage() {
        runOnUiThread(() -> {
            dateText.setText(Util.formatDateToStandard(currentDate));
            String currDateStr = Util.formatDateToStandard(currentDate);
            MessageCenter.getInstance().GetTimeslotOfCenterOnDateRequest(centerName, currDateStr, BookingActivity.this);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshPage();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            centerName = (String) b.get("CenterName");
        }

        currentDate = Calendar.getInstance().getTime();

        backtoMapButton = findViewById(R.id.bookingButtonBacktoMap);

        prevDayButton = findViewById(R.id.bookingButtonPrevDay);
        nextDayButton = findViewById(R.id.bookingButtonNextDay);
        dateText = findViewById(R.id.bookingTextViewSelectedDate);

        prevDayButton.setOnClickListener(view -> {
            Calendar cal = Calendar.getInstance();
            cal.setTime(currentDate);
            if (cal.get(Calendar.DAY_OF_YEAR) != Calendar.getInstance().get(Calendar.DAY_OF_YEAR)) {
                currentDate = Util.findPrevDay(currentDate);
                refreshPage();
            }
        });

        nextDayButton.setOnClickListener(view -> {
            currentDate = Util.findNextDay(currentDate);
            refreshPage();
        });

        backtoMapButton.setOnClickListener(view -> startActivity(new Intent(BookingActivity.this, MapsActivity.class)));

        if (centerName != null) {
            refreshPage();
        }

        TextView center_name_text = findViewById(R.id.bookingTextViewCenterName);
        SpannableString spanString = new SpannableString(centerName);
        spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
        center_name_text.setText(spanString);
    }
}
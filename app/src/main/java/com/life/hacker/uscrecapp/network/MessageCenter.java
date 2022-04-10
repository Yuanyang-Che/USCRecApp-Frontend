package com.life.hacker.uscrecapp.network;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.life.hacker.uscrecapp.Notification.NotificationEntry;
import com.life.hacker.uscrecapp.Notification.NotificationQueue;
import com.life.hacker.uscrecapp.SessionData;
import com.life.hacker.uscrecapp.Util;
import com.life.hacker.uscrecapp.activity.BookingActivity;
import com.life.hacker.uscrecapp.activity.LoginActivity;
import com.life.hacker.uscrecapp.activity.MapsActivity;
import com.life.hacker.uscrecapp.activity.NotificationCenterActivity;
import com.life.hacker.uscrecapp.activity.SignUpActivity;
import com.life.hacker.uscrecapp.activity.SummaryActivity;
import com.life.hacker.uscrecapp.model.Center;
import com.life.hacker.uscrecapp.model.Day;
import com.life.hacker.uscrecapp.model.MapData;
import com.life.hacker.uscrecapp.model.Timeslot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

import protodata.Datastructure;

public class MessageCenter {

    private final String login_uri = "http://realrecapp.herokuapp.com/user/login";
    private final String signup_uri = "http://realrecapp.herokuapp.com/user/signup";
    private final String logout_uri = "http://realrecapp.herokuapp.com/user/logout";
    private final String centerlist_uri = "http://realrecapp.herokuapp.com/service/centers";
    private final String timeslotslist_uri = "http://realrecapp.herokuapp.com/service/centers/timeslot";
    private final String book_uri = "http://realrecapp.herokuapp.com/service/book";
    private final String cancel_book_uri = "http://realrecapp.herokuapp.com/service/cancel/book";
    private final String cancel_waitlist_uri = "http://realrecapp.herokuapp.com/service/cancel/waitlist";
    private final String waitlist_uri = "http://realrecapp.herokuapp.com/service/waitlist";
    private final String history_uri = "http://realrecapp.herokuapp.com/service/history";
    private final String notification_uri = "http://realrecapp.herokuapp.com/service/notification";


    private ConcurrentHashMap<Long, Context> task = new ConcurrentHashMap<>();

    private ConcurrentHashMap<Long, BookCaller> book_task = new ConcurrentHashMap<>();

    private ConnectionCenter center = new ConnectionCenter();

    private MessageCenter() {}

    private static final class InstanceHolder {
        private static final MessageCenter instance = new MessageCenter();
    }

    public static MessageCenter getInstance() {
        return InstanceHolder.instance;
    }

    public void LoginRequest(String email, String password, Context context) {
        Datastructure.LoginRequest request = Datastructure.LoginRequest.newBuilder().setEmail(email).setPassword(password).build();
        long task_id = center.SendMessagePost(login_uri, request.toByteArray(), "");
        task.put(task_id, context);
    }

    public void SignupRequest(String email, String student_id, String username, String password, byte[] avatar, Context context) {
        Datastructure.SignupRequest request = Datastructure.SignupRequest.newBuilder().setEmail(email).setPassword(password).setUsername(username).setUscstudentid(student_id).setAvatar(ByteString.copyFrom(avatar)).build();
        long task_id = center.SendMessagePost(signup_uri, request.toByteArray(), "");
        task.put(task_id, context);
    }

    public void LogoutRequest(String user_token, Context context) {
        center.SendMessageGet(logout_uri, user_token);
        logout(context);
    }

    public void GetCenterlistRequest(Context context) {
        long task_id = center.SendMessageGet(centerlist_uri, "");
        task.put(task_id, context);
    }

    public void GetTimeslotOfCenterOnDateRequest(String center_name, String date, Context context) {
        Datastructure.TimeslotOnDateRequest request = Datastructure.TimeslotOnDateRequest.newBuilder().setCentername(center_name).setDate(date).build();
        long task_id = center.SendMessagePost(timeslotslist_uri, request.toByteArray(), SessionData.getInstance().getToken());
        task.put(task_id, context);
    }

    // yyyy-mm-dd, 08:00:00
    public void BookRequest(String center_name, String date, String timeslot, String user_token, Context context) {
        Datastructure.BookRequest request = Datastructure.BookRequest.newBuilder().setCentername(center_name).setDate(date).setTimeslot(timeslot).build();
        long task_id = center.SendMessagePost(book_uri, request.toByteArray(), user_token);
        book_task.put(task_id, new BookCaller(context, true));
    }

    // yyyy-mm-dd, 08:00:00
    public void NotificationBookRequest(String center_name, String date, String timeslot, String user_token, Context context) {
        Datastructure.BookRequest request = Datastructure.BookRequest.newBuilder().setCentername(center_name).setDate(date).setTimeslot(timeslot).build();
        long task_id = center.SendMessagePost(book_uri, request.toByteArray(), user_token);
        book_task.put(task_id, new BookCaller(context, false));
    }

    public void CancelBookRequest(String center_name, String date, String timeslot, String user_token, Context context) {
        Datastructure.CancelRequest request = Datastructure.CancelRequest.newBuilder().setCentername(center_name).setDate(date).setTimeslot(timeslot).build();
        long task_id = center.SendMessagePost(cancel_book_uri, request.toByteArray(), user_token);
        task.put(task_id, context);
    }

    public void CancelWaitlistRequest(String center_name, String date, String timeslot, String user_token, Context context) {
        Datastructure.CancelRequest request = Datastructure.CancelRequest.newBuilder().setCentername(center_name).setDate(date).setTimeslot(timeslot).build();
        long task_id = center.SendMessagePost(cancel_waitlist_uri, request.toByteArray(), user_token);
        task.put(task_id, context);
    }

    public void WaitlistRequest(String center_name, String date, String timeslot, String user_token, Context context) {
        Datastructure.WaitlistRequest request = Datastructure.WaitlistRequest.newBuilder().setCentername(center_name).setDate(date).setTimeslot(timeslot).build();
        long task_id = center.SendMessagePost(waitlist_uri, request.toByteArray(), user_token);
        task.put(task_id, context);
    }

    public void HistoryRequest(String user_token, Context context) {
        long task_id = center.SendMessagePost(history_uri, new byte[0], user_token);
        task.put(task_id, context);
    }

    public void NotificationRequest(String user_token) {
        center.SendMessageGet(notification_uri, user_token);
    }

    public void MessageResponse(byte[] raw_data, String uri, long task_id) {
        try {
            switch (uri) {
                case login_uri: {
                    Datastructure.LoginResponse response = Datastructure.LoginResponse.parseFrom(raw_data);
                    LoginResponse(response, task_id);
                    break;
                }
                case signup_uri: {
                    Datastructure.SignupResponse response = Datastructure.SignupResponse.parseFrom(raw_data);
                    SignupResponse(response, task_id);
                    break;
                }
                case centerlist_uri: {
                    Datastructure.CenterResponse response = Datastructure.CenterResponse.parseFrom(raw_data);
                    GetCenterlistResponse(response, task_id);
                    break;
                }
                case timeslotslist_uri: {
                    Datastructure.TimeslotOnDateResponse response = Datastructure.TimeslotOnDateResponse.parseFrom(raw_data);
                    GetTimeslotOfCenterOnDateResponse(response, task_id);
                    break;
                }
                case book_uri: {
                    Datastructure.BookResponse response = Datastructure.BookResponse.parseFrom(raw_data);
                    BookResponse(response, task_id);
                    break;
                }
                case cancel_book_uri: {
                    Datastructure.CancelResponse response = Datastructure.CancelResponse.parseFrom(raw_data);
                    CancelBookResponse(response, task_id);
                    break;
                }
                case cancel_waitlist_uri: {
                    Datastructure.CancelResponse response = Datastructure.CancelResponse.parseFrom(raw_data);
                    CancelWaitlistResponse(response, task_id);
                    break;
                }
                case waitlist_uri: {
                    Datastructure.WaitlistResponse response = Datastructure.WaitlistResponse.parseFrom(raw_data);
                    WaitlistResponse(response, task_id);
                    break;
                }
                case history_uri: {
                    Datastructure.HistoryResponse response = Datastructure.HistoryResponse.parseFrom(raw_data);
                    HistoryResponse(response, task_id);
                    break;
                }
                case notification_uri: {
                    Datastructure.NotificationResponse response = Datastructure.NotificationResponse.parseFrom(raw_data);
                    NotificationResponse(response);
                    break;
                }
            }
        } catch (InvalidProtocolBufferException e) {
            System.out.println(e.getMessage());
            // most likely is the backend error.
        }
    }

    public void LoginResponse(Datastructure.LoginResponse response, long task_id) {
        LoginActivity context = (LoginActivity) task.get(task_id);
        assert context != null;

        Datastructure.LoginResponse.Error error = response.getErr();
        if (error != Datastructure.LoginResponse.Error.GOOD) {
            String errorMsg = null;
            switch (error.getNumber()) {
                case Datastructure.LoginResponse
                        .Error.NOTUSED_VALUE:
                case Datastructure.LoginResponse
                        .Error.GOOD_VALUE:
                    errorMsg = "Impossible";
                    break;
                case Datastructure.LoginResponse
                        .Error.INVALID_EMAIL_PASSWORD_VALUE:
                    errorMsg = "Incorrect Email or password";
                    break;
                case Datastructure.LoginResponse.Error.SERVER_VALUE:
                    errorMsg = "Server Error Please try again";
                    break;
            }
            context.takeErrorMessage(errorMsg);
            return;
        }


        String email = response.getEmail();
        String username = response.getUsername();
        String netid = response.getUscstudentid();
        String token = response.getTokens();

        Bitmap decodedByte = Util.decompressBytesToBitmap(response.getAvatar().toByteArray());

        //Store the user login info and token
        SessionData.getInstance().setUser(email, username, netid, decodedByte);
        SessionData.getInstance().setToken(token);

        Util.storeSessionToStorage(context);

        //Jump to main screen
        loginSuccess(context);
    }

    public void loginSuccess(Context context) {
        //Means we start to listen to Notifications
        //TODO start websockets ...
        if (!NotificationCenter.GetInstance().IsStart()) {
            NotificationCenter.GetInstance().Start();
        }
        //Start websocket...
        context.startActivity(new Intent(context, MapsActivity.class));
    }

    public void SignupResponse(Datastructure.SignupResponse response, long task_id) {
        SignUpActivity context = (SignUpActivity) task.get(task_id);
        assert context != null;

        Datastructure.SignupResponse.Error error = response.getErr();
        if (error != Datastructure.SignupResponse.Error.GOOD) {
            String errorMsg = null;
            switch (error.getNumber()) {
                case Datastructure.SignupResponse
                        .Error.NOTUSED_VALUE:
                case Datastructure.SignupResponse
                        .Error.GOOD_VALUE:
                    errorMsg = "Impossible";
                    break;
                case Datastructure.SignupResponse
                        .Error.INVALID_EMAIL_VALUE:
                    errorMsg = "Incorrect Email";
                    break;
                case Datastructure.SignupResponse.
                        Error.INVALID_USERNAME_VALUE:
                    errorMsg = "Server Error Username";
                    break;
            }
            context.takeErrorMessage(errorMsg);
            return;
        }

        String email = response.getEmail();
        String username = response.getUsername();
        String netid = response.getUscstudentid();
        Bitmap avatar = Util.decompressBytesToBitmap(response.getAvatar().toByteArray());
        String token = response.getTokens();

        SessionData.getInstance().setUser(email, username, netid, avatar);
        SessionData.getInstance().setToken(token);

        if (!NotificationCenter.GetInstance().IsStart()) {
            NotificationCenter.GetInstance().Start();
        }

        context.startActivity(new Intent(context, MapsActivity.class));
    }

    public void LogoutResponse(long task_id) {
        MapsActivity context = (MapsActivity) task.get(task_id);
        logout(context);
    }

    public void logout(Context context) {
        assert context != null;
        NotificationCenter.GetInstance().Stop();
        SessionData.getInstance().clearSession();

        //Also clear the Shared Pref Data
        Util.clearSessionFromStorage(context);
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    public void GetCenterlistResponse(Datastructure.CenterResponse response, long task_id) {
        MapsActivity context = (MapsActivity) task.get(task_id);
        assert context != null;

        List<Datastructure.Center> centers = response.getCenterlistList();
        //System.out.println(Arrays.asList(response.getCenterlistList()));
        List<Center> centerList = new ArrayList<>();
        for (Datastructure.Center c : centers) {
            centerList.add(new Center(0, c.getName(), new Day[3], c.getLatitude(), c.getLongitude()));
        }

        context.runOnUiThread(() -> context.setCenters(centerList));
    }

    public void GetTimeslotOfCenterOnDateResponse(Datastructure.TimeslotOnDateResponse response, long task_id) {
        BookingActivity context = (BookingActivity) task.get(task_id);
        assert context != null;

        List<Datastructure.TimeslotUsernum> timeslots = response.getListList();
        List<Timeslot> timeslotList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        Date currDate = calendar.getTime();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);

        Date requestDate = context.getCurrentDate();

        for (Datastructure.TimeslotUsernum t : timeslots) {
            int timeIndex = Integer.parseInt(t.getTimeslot().substring(0, 2));
            if (requestDate.after(currDate) || timeIndex > hours) {
                timeslotList.add(new Timeslot(timeIndex, Util.Capacity,
                        t.getUsernum(), new Day(requestDate, null, null), false,
                        t.getIsbooked(), t.getIswaitlisted()));
            }
        }

        context.runOnUiThread(() -> context.setTimeSlotList(timeslotList));
    }

    public void BookResponse(Datastructure.BookResponse response, long task_id) {
        //TODO
        BookCaller caller = book_task.get(task_id);

        if(caller.is_from_booking) {
            BookResponseFromBook(response, task_id, (BookingActivity)caller.c);
        } else {
            BookResponseFromNotification(response, task_id, (NotificationCenterActivity) caller.c);
        }
    }

    private void BookResponseFromBook(Datastructure.BookResponse response, long task_id, BookingActivity context) {
        assert context != null;

        // if the error code means the user token is expired
        // call LogoutResponse()
        String message = response.getErr().getNumber() == Datastructure.BookResponse.Error.GOOD_VALUE ?
                "Book Success" : "Something went wrong, Error Code " + response.getErr().getNumber();
        context.runOnUiThread(() -> context.jumpBackToMap(message));
    }

    private void BookResponseFromNotification(Datastructure.BookResponse response, long task_id, NotificationCenterActivity context) {
        assert context != null;

        // if the error code means the user token is expired
        // call LogoutResponse()
        String message = response.getErr().getNumber() == Datastructure.BookResponse.Error.GOOD_VALUE ?
                "Book Success" : "Something went wrong, Error Code " + response.getErr().getNumber();
        context.takeToastMessage(message);
        context.recreateListView();
    }

    public void CancelBookResponse(Datastructure.CancelResponse response, long task_id) {
        SummaryActivity context = (SummaryActivity) task.get(task_id);
        if (context != null) {
            context.refreshPage();

            if (response.getErr().getNumber() != Datastructure.CancelResponse.Error.GOOD_VALUE) {
                context.takeToastMessage("Something went wrong in cancellation");
            } else {
                context.takeToastMessage("Cancel success");
            }
        }

    }

    public void CancelWaitlistResponse(Datastructure.CancelResponse response, long task_id) {
        NotificationCenterActivity context = (NotificationCenterActivity) task.get(task_id);

        if (context != null) {
            context.refreshPage();

            if (response.getErr().getNumber() != Datastructure.CancelResponse.Error.GOOD_VALUE) {
                context.takeToastMessage("Something went wrong in cancellation");
            } else {
                context.takeToastMessage("Cancel Waitlist success");
            }

            context.recreateListView();
        }

    }

    public void WaitlistResponse(Datastructure.WaitlistResponse response, long task_id) {
        BookingActivity context = (BookingActivity) task.get(task_id);
        if (context != null) {
            String message = response.getErr().getNumber() == Datastructure.BookResponse.Error.GOOD_VALUE ?
                    "Waitlist Success" : "Something went wrong, Error Code " + response.getErr().getNumber();
            context.takeToastMessage(message);
            context.refreshPage();
        }
    }


    //F**K Java, this should be a lambda function, but java's lambda sucks so i gotta declare it
    private void addTo(List<Timeslot> timeslots, List<Datastructure.BookingEntry> list, boolean isPast) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        for (Datastructure.BookingEntry p : list) {
            int timeslotIdx = Integer.parseInt(p.getTimeslot().substring(0, 2));

            Date date = null;
            try {
                date = format.parse(p.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Day day = new Day(date, MapData.getInstance().findCenterByName(p.getCentername()), null);
            timeslots.add(new Timeslot(timeslotIdx, Util.Capacity, 0, day, isPast, true, false));
        }
    }

    public void HistoryResponse(Datastructure.HistoryResponse response, long task_id) {
        SummaryActivity context = (SummaryActivity) task.get(task_id);
        assert context != null;

        List<Timeslot> timeslots = new ArrayList<>();

        List<Datastructure.BookingEntry> past = response.getPreviousList();
        List<Datastructure.BookingEntry> upcoming = response.getUpcomingList();
        addTo(timeslots, past, true);
        addTo(timeslots, upcoming, false);

        context.runOnUiThread(() -> context.update(timeslots));
    }

    public void NotificationResponse(Datastructure.NotificationResponse response) {
        // Get notifications. Notification might have multiple entries
        //TODO add notification
        List<Datastructure.NotificationEntry> notification_list = response.getListList();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        for (Datastructure.NotificationEntry entry : notification_list) {
            int timeIndex = Integer.parseInt(entry.getTimeslot().substring(0, 2));

            Date date = null;
            try {
                date = format.parse(entry.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            NotificationQueue.getInstance().addTimeslot(new NotificationEntry(timeIndex, date, entry.getCentername()));
        }
    }
}


// we might call from different places
class BookCaller {
    public Context c;
    public boolean is_from_booking;

    public BookCaller(Context c, boolean is_from_booking) {
        this.c = c;
        this.is_from_booking = is_from_booking;
    }
}
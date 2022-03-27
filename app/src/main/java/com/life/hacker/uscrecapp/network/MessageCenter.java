package com.life.hacker.uscrecapp.network;

import android.content.Intent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MapEntryLite;
import com.life.hacker.uscrecapp.SessionData;
import com.life.hacker.uscrecapp.activity.BookingActivity;
import com.life.hacker.uscrecapp.activity.LoginActivity;
import com.life.hacker.uscrecapp.activity.MapsActivity;
import com.life.hacker.uscrecapp.activity.SignUpActivity;
import com.life.hacker.uscrecapp.model.Center;
import com.life.hacker.uscrecapp.model.Day;
import com.life.hacker.uscrecapp.model.Timeslot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import java.util.concurrent.ConcurrentHashMap;

import protodata.Datastructure;

public class MessageCenter {

    private final String login_uri = "http://realrecapp.herokuapp.com/user/login";
    private final String signup_uri = "http://realrecapp.herokuapp.com/user/signup";
    private final String logout_uri = "http://realrecapp.herokuapp.com/user/logout";
    private final String centerlist_uri = "http://realrecapp.herokuapp.com/service/centers";
    private final String timeslotslist_uri = "http://realrecapp.herokuapp.com/service/centers/timeslot";
    private final String book_uri = "http://realrecapp.herokuapp.com/service/book";
    private final String cancel_uri = "http://realrecapp.herokuapp.com/service/cancel";
    private final String waitlist_uri = "http://realrecapp.herokuapp.com/service/waitlist";
    private final String history_uri = "http://realrecapp.herokuapp.com/service/history";
    private final String notification_uri = "http://realrecapp.herokuapp.com/service/notification";


    private ConcurrentHashMap<Long, Context> callers = new ConcurrentHashMap<>();

    private ConnectionCenter center = new ConnectionCenter();

    private MessageCenter() {}

    private static final class InstanceHolder {
        private static final MessageCenter instance = new MessageCenter();
    }

    public static MessageCenter GetInstance() {
        return InstanceHolder.instance;
    }

    public void LoginRequest(String email, String password, Context context) {
        Datastructure.LoginRequest request = Datastructure.LoginRequest.newBuilder().setEmail(email).setPassword(password).build();
        long task_id = center.SendMessagePost(login_uri, request.toByteArray(), "");
        callers.put(task_id, context);
    }

    public void SignupRequest(String email, String student_id, String username, String password, byte[] avatar, Context context) {
        Datastructure.SignupRequest request = Datastructure.SignupRequest.newBuilder().setEmail(email).setPassword(password).setUsername(username).setUscstudentid(student_id).setAvatar(ByteString.copyFrom(avatar)).build();
        long task_id = center.SendMessagePost(signup_uri, request.toByteArray(), "");
        callers.put(task_id, context);
    }

    public void LogoutRequest(String email, String student_id, String password, String user_token, Context context) {
        Datastructure.SignupRequest request = Datastructure.SignupRequest.newBuilder().setEmail(email).setPassword(password).setUscstudentid(student_id).build();
        long task_id = center.SendMessagePost(logout_uri, request.toByteArray(), user_token);
        callers.put(task_id, context);
    }

    public void GetCenterlistRequest(Context context) {
        long task_id = center.SendMessageGet(centerlist_uri, "");
        callers.put(task_id, context);
    }

    public void GetTimeslotOfCenterOnDateRequest(String center_name, String date, Context context) {
        Datastructure.TimeslotOnDateRequest request = Datastructure.TimeslotOnDateRequest.newBuilder().setCentername(center_name).setDate(date).build();
        long task_id = center.SendMessagePost(timeslotslist_uri, request.toByteArray(), SessionData.getInstance().getToken());
        callers.put(task_id, context);
    }
    // yyyy-mm-dd, 08:00:00
    public void BookRequest(String center_name, String date, String timeslot, String user_token, Context context) {
        Datastructure.BookRequest request = Datastructure.BookRequest.newBuilder().setCentername(center_name).setDate(date).setTimeslot(timeslot).build();
        long task_id = center.SendMessagePost(book_uri, request.toByteArray(), user_token);
        callers.put(task_id, context);
    }

    public void CancelRequest(String center_name, String date, String timeslot, String user_token, Context context) {
        Datastructure.CancelRequest request = Datastructure.CancelRequest.newBuilder().setCentername(center_name).setDate(date).setTimeslot(timeslot).build();
        long task_id = center.SendMessagePost(cancel_uri, request.toByteArray(), user_token);
        callers.put(task_id, context);
    }

    public void WaitlistRequest(String center_name, String date, String timeslot, String user_token, Context context) {
        Datastructure.WaitlistRequest request = Datastructure.WaitlistRequest.newBuilder().setCentername(center_name).setDate(date).setTimeslot(timeslot).build();
        long task_id = center.SendMessagePost(waitlist_uri, request.toByteArray(), user_token);
        callers.put(task_id, context);
    }

    public void HistoryRequest(String center_name, String date, String timeslot, String user_token, Context context) {
        long task_id = center.SendMessagePost(history_uri, new byte[0], user_token);
        callers.put(task_id, context);
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
                case logout_uri: {
                    LogoutResponse(task_id);
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
                case cancel_uri: {
                    Datastructure.CancelResponse response = Datastructure.CancelResponse.parseFrom(raw_data);
                    CancelResponse(response, task_id);
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
        LoginActivity context = (LoginActivity) callers.get(task_id);
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

        Bitmap decodedByte = null;

        if (response.getAvatar() != null) {
            byte[] arr = response.getAvatar().toByteArray();

            decodedByte = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        }

        //Store the user login info and token
        SessionData.getInstance().setUser(email, username, netid, decodedByte);
        SessionData.getInstance().setToken(token);

        //Jump to main screen
        //Same code here
        loginSuccess(context);
    }

    public void loginSuccess(Context context) {
        //Means we start to listen to Notifications
        //TODO start websockets ...
        if(!NotificationCenter.GetInstance().IsStart()) {
            NotificationCenter.GetInstance().Start();
        }
        //Start websocket...
        context.startActivity(new Intent(context, MapsActivity.class));
    }

    public void SignupResponse(Datastructure.SignupResponse response, long task_id) {
        SignUpActivity context = (SignUpActivity) callers.get(task_id);
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
            //context.takeErrorMessage(errorMsg);
            return;
        }
        String email = response.getEmail();
        String token = response.getTokens();

        context.startActivity(new Intent(context, MapsActivity.class));
    }

    public void LogoutResponse(long task_id) {

    }

    public void GetCenterlistResponse(Datastructure.CenterResponse response, long task_id) {
        //What to do with center list data?
        //Datastructure.Center c = response.getCenterlist(0);
        MapsActivity context = (MapsActivity) callers.get(task_id);

        List<Datastructure.Center> centers = response.getCenterlistList();
        System.out.println(Arrays.asList(response.getCenterlistList()));
        List<Center> centerList = new ArrayList<>();
        for (Datastructure.Center c : centers) {
            centerList.add(new Center(0, c.getName(), new Day[3], c.getLatitude(), c.getLongitude()));
        }

        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                context.setCenters(centerList);
            }
        });
        //Pass it back to the activity that needs these data.
        //Wait. Which activity?
    }

    public void GetTimeslotOfCenterOnDateResponse(Datastructure.TimeslotOnDateResponse response, long task_id) {
        BookingActivity context = (BookingActivity) callers.get(task_id);

        List<Datastructure.TimeslotUsernum> timeslots = response.getListList();
        List<Timeslot> timeslotList = new ArrayList<>();
        for(Datastructure.TimeslotUsernum t : timeslots) {
            timeslotList.add(new Timeslot(Integer.parseInt(t.getTimeslot().substring(0, 2)),
                    2, (int)t.getUsernum(), new HashSet<>(), new Day(), false, t.getIsbooked()));
        }

        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                context.setTimeSlotList(timeslotList);
            }
        });

    }

    public void BookResponse(Datastructure.BookResponse response, long task_id) {
        BookingActivity context = (BookingActivity) callers.get(task_id);

        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                context.jumpBackToMap();
            }
        });
    }

    public void CancelResponse(Datastructure.CancelResponse response, long task_id) {

    }

    public void WaitlistResponse(Datastructure.WaitlistResponse response, long task_id) {

    }

    public void HistoryResponse(Datastructure.HistoryResponse response, long task_id) {

    }

    public void NotificationResponse(Datastructure.NotificationResponse response) {
        // Get notifications. Notification might have multiple entries
        System.out.println(response.getListCount());
    }
}

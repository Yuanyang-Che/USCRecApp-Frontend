package com.life.hacker.uscrecapp.network;

import android.content.Intent;
import android.app.Activity;
import android.content.Context;

import com.google.protobuf.InvalidProtocolBufferException;
import com.life.hacker.uscrecapp.activity.LoginActivity;
import com.life.hacker.uscrecapp.activity.MapsActivity;

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

    public void SignupRequest(String email, String student_id, String password, Context context) {
        Datastructure.SignupRequest request = Datastructure.SignupRequest.newBuilder().setEmail(email).setPassword(password).setUscstudentid(student_id).build();
        long task_id = center.SendMessagePost(signup_uri, request.toByteArray(), "");
        callers.put(task_id, context);
    }

    public void LogoutRequest(String email, String student_id, String password, String user_token, Context context) {
        Datastructure.SignupRequest request = Datastructure.SignupRequest.newBuilder().setEmail(email).setPassword(password).setUscstudentid(student_id).build();
        long task_id = center.SendMessagePost(logout_uri, request.toByteArray(), user_token);
        callers.put(task_id, context);
    }

    public void GetCenterlistRequest(Context context) {
        long task_id = center.SendMessageGet(centerlist_uri);
        callers.put(task_id, context);
    }

    public void GetTimeslotOfCenterOnDateRequest(String center_name, String date, Context context) {
        Datastructure.TimeslotOnDateRequest request = Datastructure.TimeslotOnDateRequest.newBuilder().setCentername(center_name).setDate(date).build();
        long task_id = center.SendMessagePost(timeslotslist_uri, request.toByteArray(), "");
        callers.put(task_id, context);
    }

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
            }
        } catch (InvalidProtocolBufferException e) {
            System.out.println(e.getMessage());
            // most likely is the backend error.
        }

    }


    public void LoginResponse(Datastructure.LoginResponse response, long task_id) {
        LoginActivity context = (LoginActivity) callers.get(task_id);

        Datastructure.LoginResponse.Error error = response.getErr();
        if (error != Datastructure.LoginResponse.Error.GOOD) {
            context.takeErrorMessage(error.toString());
            return;
        }

        // get username, password etc by getUsername getPassword

        String email = response.getEmail();
        String username = response.getUsername();
        String token = response.getTokens();
        //Store the user login info and token

        //Jump to main screen
        context.startActivity(new Intent(context, MapsActivity.class));

        //Send back an error message prompt

    }

    public void SignupResponse(Datastructure.SignupResponse response, long task_id) {

    }

    public void LogoutResponse(long task_id) {

    }

    public void GetCenterlistResponse(Datastructure.CenterResponse response, long task_id) {
        //What to do with center list data?
        //Datastructure.Center c = response.getCenterlist(0);

        //Pass it back to the activity that needs these data.
        //Wait. Which activity?
    }

    public void GetTimeslotOfCenterOnDateResponse(Datastructure.TimeslotOnDateResponse response, long task_id) {

    }

    public void BookResponse(Datastructure.BookResponse response, long task_id) {

    }

    public void CancelResponse(Datastructure.CancelResponse response, long task_id) {

    }

    public void WaitlistResponse(Datastructure.WaitlistResponse response, long task_id) {

    }

    public void HistoryResponse(Datastructure.HistoryResponse response, long task_id) {

    }
}

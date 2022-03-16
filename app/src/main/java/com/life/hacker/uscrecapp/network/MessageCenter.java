package com.life.hacker.uscrecapp.network;

import com.google.protobuf.InvalidProtocolBufferException;

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


    private ConnectionCenter center = new ConnectionCenter();

    private MessageCenter() {}

    private static final class InstanceHolder {
        private static final MessageCenter instance = new MessageCenter();
    }

    public static MessageCenter GetInstance() {
        return InstanceHolder.instance;
    }

    public void LoginRequest(String email, String password) {
        Datastructure.LoginRequest request = Datastructure.LoginRequest.newBuilder().setEmail(email).setPassword(password).build();
        center.SendMessagePost(login_uri, request.toByteArray(), "");
    }

    public void SignupRequest(String email, String student_id, String password) {
        Datastructure.SignupRequest request = Datastructure.SignupRequest.newBuilder().setEmail(email).setPassword(password).setUscstudentid(student_id).build();
        center.SendMessagePost(signup_uri, request.toByteArray(), "");
    }

    public void LogoutRequest(String email, String student_id, String password) {
        Datastructure.SignupRequest request = Datastructure.SignupRequest.newBuilder().setEmail(email).setPassword(password).setUscstudentid(student_id).build();
        center.SendMessagePost(logout_uri, request.toByteArray(), "");
    }

    public void GetCenterlistRequest() {
        center.SendMessageGet(centerlist_uri);
    }

    public void GetTimeslotOfCenterOnDateRequest(String center_name, String date) {
        Datastructure.TimeslotOnDateRequest request = Datastructure.TimeslotOnDateRequest.newBuilder().setCentername(center_name).setDate(date).build();
        center.SendMessagePost(timeslotslist_uri, request.toByteArray(), "");
    }

    public void BookRequest(String center_name, String date, String timeslot) {
        Datastructure.BookRequest request = Datastructure.BookRequest.newBuilder().setCentername(center_name).setDate(date).setTimeslot(timeslot).build();
        center.SendMessagePost(book_uri, request.toByteArray(), "");
    }

    public void CancelRequest(String center_name, String date, String timeslot) {
        Datastructure.CancelRequest request = Datastructure.CancelRequest.newBuilder().setCentername(center_name).setDate(date).setTimeslot(timeslot).build();
        center.SendMessagePost(cancel_uri, request.toByteArray(), "");
    }

    public void WaitlistRequest(String center_name, String date, String timeslot) {
        Datastructure.WaitlistRequest request = Datastructure.WaitlistRequest.newBuilder().setCentername(center_name).setDate(date).setTimeslot(timeslot).build();
        center.SendMessagePost(waitlist_uri, request.toByteArray(), "");
    }

    public void HistoryRequest(String center_name, String date, String timeslot) {
        center.SendMessagePost(history_uri, new byte[0], "");
    }

    public void MessageResponse(byte[] raw_data, String uri) {
        try {
            switch(uri) {
                case login_uri: {
                    Datastructure.LoginResponse response = Datastructure.LoginResponse.parseFrom(raw_data);
                    LoginResponse(response);
                    break;
                }
                case signup_uri: {
                    Datastructure.SignupResponse response = Datastructure.SignupResponse.parseFrom(raw_data);
                    SignupResponse(response);
                    break;
                }
                case logout_uri: {
                    LogoutResponse();
                    break;
                }
                case centerlist_uri: {
                    Datastructure.CenterResponse response = Datastructure.CenterResponse.parseFrom(raw_data);
                    GetCenterlistResponse(response);
                    break;
                }
                case timeslotslist_uri: {
                    Datastructure.TimeslotOnDateResponse response = Datastructure.TimeslotOnDateResponse.parseFrom(raw_data);
                    GetTimeslotOfCenterOnDateResponse(response);
                    break;
                }
                case book_uri: {
                    Datastructure.BookResponse response = Datastructure.BookResponse.parseFrom(raw_data);
                    BookResponse(response);
                    break;
                }
                case cancel_uri: {
                    Datastructure.CancelResponse response = Datastructure.CancelResponse.parseFrom(raw_data);
                    CancelResponse(response);
                    break;
                }
                case waitlist_uri: {
                    Datastructure.WaitlistResponse response = Datastructure.WaitlistResponse.parseFrom(raw_data);
                    WaitlistResponse(response);
                    break;
                }
                case history_uri: {
                    Datastructure.HistoryResponse response = Datastructure.HistoryResponse.parseFrom(raw_data);
                    HistoryResponse(response);
                    break;
                }
            }
        } catch (InvalidProtocolBufferException e) {
            System.out.println(e.getMessage());
            // most likely is the backend error.
        }

    }

    public void LoginResponse(Datastructure.LoginResponse response) {
        // get username, password etc by getUsername getPassword
    }

    public void SignupResponse(Datastructure.SignupResponse response) {

    }

    public void LogoutResponse() {

    }

    public void GetCenterlistResponse(Datastructure.CenterResponse response) {

    }

    public void GetTimeslotOfCenterOnDateResponse(Datastructure.TimeslotOnDateResponse response) {

    }

    public void BookResponse(Datastructure.BookResponse response) {

    }

    public void CancelResponse(Datastructure.CancelResponse response) {

    }

    public void WaitlistResponse(Datastructure.WaitlistResponse response) {

    }

    public void HistoryResponse(Datastructure.HistoryResponse response) {

    }
}

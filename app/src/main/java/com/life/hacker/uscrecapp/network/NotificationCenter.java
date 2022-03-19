package com.life.hacker.uscrecapp.network;

import com.google.protobuf.InvalidProtocolBufferException;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import protodata.Datastructure;

public class NotificationCenter {
    private final String socket_uri = "http://realrecapp.herokuapp.com/service/notification";
    private OkHttpClient client_ = new OkHttpClient().newBuilder().connectTimeout(2, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
    EchoWebSocketListener listener_;
    WebSocket socket_;
    private static final int CLOSE_STATUS = 1000;

    public NotificationCenter() {
    }

    public void Connect() {
        if(socket_ != null) {
            socket_.close(CLOSE_STATUS, "reconnect");
        }
        Request request = new Request.Builder().url(socket_uri).header("token", "").build();
        listener_ = new EchoWebSocketListener(this);
        socket_ = client_.newWebSocket(request, listener_);
    }
}

 final class EchoWebSocketListener extends WebSocketListener {
    private static final int CLOSE_STATUS = 1000;
     NotificationCenter notification_center_;
    public EchoWebSocketListener (NotificationCenter notification_center) {
        notification_center_ = notification_center;
    }
    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        try {
            Datastructure.Notification notification = Datastructure.Notification.parseFrom(bytes.toByteArray());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }
    @Override
     public void onClosing(WebSocket webSocket, int code, String reason) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notification_center_.Connect();
        notification_center_ = null;
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable throwable, Response response) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notification_center_.Connect();
        notification_center_ = null;
    }
}

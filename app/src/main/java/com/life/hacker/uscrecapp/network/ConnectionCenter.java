package com.life.hacker.uscrecapp.network;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

class ConnectionCenter {

    private final OkHttpClient client = new OkHttpClient();

    public ConnectionCenter() {
    }

    public void SendMessagePost(String url, byte[] body, String user_token) {
            Request request = new Request.Builder().url(url).header("token", user_token).
                    post(RequestBody.create(body, MediaType.parse("application/x-protobuf"))).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    // failed. most likely is backend error
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    MessageCenter.GetInstance().MessageResponse(response.body().bytes(), url);
                }
            });
    }
    public void SendMessageGet(String url) {
        Request request = new Request.Builder().url(url).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // failed. most likely is backend error
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                MessageCenter.GetInstance().MessageResponse(response.body().bytes(), url);
            }
        });
    }
}
package com.life.hacker.uscrecapp.network;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

class ConnectionCenter {

    private final OkHttpClient client = new OkHttpClient();

    private Set<Long> task_id_pool_;

    public ConnectionCenter() {
        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
        task_id_pool_ = map.newKeySet();
    }

    private long GenerateTaskId() {
        long num = ThreadLocalRandom.current().nextLong();

        while (task_id_pool_.contains(num)) {
            num = ThreadLocalRandom.current().nextLong();
        }

        task_id_pool_.add(num);

        return num;
    }

    private void RemoveTaskId(long task_id) {
        task_id_pool_.remove(task_id);
    }

    public long SendMessagePost(String url, byte[] body, String user_token) {
        long task_id = GenerateTaskId();
        Request request = new Request.Builder().url(url).header("token", user_token).
                post(RequestBody.create(body, MediaType.parse("application/x-protobuf"))).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // failed. most likely is backend error
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                MessageCenter.GetInstance().MessageResponse(response.body().bytes(), url, task_id);
                RemoveTaskId(task_id);
            }
        });


        return task_id;
    }

    public long SendMessageGet(String url) {
        long task_id = GenerateTaskId();
        Request request = new Request.Builder().url(url).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // failed. most likely is backend error
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                MessageCenter.GetInstance().MessageResponse(response.body().bytes(), url, task_id);
                RemoveTaskId(task_id);
            }
        });

        return task_id;
    }
}
package com.life.hacker.uscrecapp.network;

import com.life.hacker.uscrecapp.SessionData;

import protodata.Datastructure;

public class NotificationCenter {
    public NotificationThread t_;
    private static final class InstanceHolder {
        private static final NotificationCenter instance = new NotificationCenter();
    }

    public static NotificationCenter GetInstance() {
        return NotificationCenter.InstanceHolder.instance;
    }

    private NotificationCenter() {
        t_ = new NotificationThread();
        t_.start();
    }

    public void Start() {
        t_.SetNeedSend(true);
    }

    public boolean IsStart() {
        return t_.GetNeedSend();
    }

    public void Stop() {
        t_.SetNeedSend(false);
    }
}

class NotificationThread extends Thread {
    private boolean need_send_ = false;

    public void SetNeedSend(boolean need_send) {
        need_send_ = need_send;
    }

    public boolean GetNeedSend() {
        return need_send_;
    }

    @Override
    public void run() {
        while(true) {
            if(need_send_) {
                MessageCenter.GetInstance().NotificationRequest(SessionData.getInstance().getToken());
            }
            try {
                sleep(10000);
            } catch (InterruptedException e) {
                System.out.println("wake up");
            }
        }

    }
}

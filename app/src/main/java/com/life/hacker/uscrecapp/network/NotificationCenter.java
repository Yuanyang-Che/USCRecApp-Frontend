package com.life.hacker.uscrecapp.network;

import com.life.hacker.uscrecapp.SessionData;

import protodata.Datastructure;

public class NotificationCenter {
    public Thread t;
    public NotificationCenter() {
        t = new NotificationThread();
        t.start();
    }
}

class NotificationThread extends Thread {

    @Override
    public void run() {
        while(true) {
            MessageCenter.GetInstance().NotificationRequest(SessionData.getInstance().getToken());
            try {
                sleep(10000);
            } catch (InterruptedException e) {
                System.out.println("wake up");
            }
        }

    }
}

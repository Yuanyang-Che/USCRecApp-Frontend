package com.life.hacker.uscrecapp.Feature3Test;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import android.content.Context;

import com.life.hacker.uscrecapp.Notification.NotificationEntry;
import com.life.hacker.uscrecapp.Notification.NotificationListener;
import com.life.hacker.uscrecapp.Notification.NotificationQueue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

class ListenerTester implements NotificationListener {
    private ListenerTest t;
    public ListenerTester(ListenerTest t) {
        this.t = t;
    }

    @Override
    public void OnNotification() {
        t.calledTimes ++;
    }
}

public class ListenerTest {
    public int calledTimes = 0;


    @Test
    public void addListenerTest() {
        calledTimes = 0;
        // add one observer should call OnNotification for once
        ListenerTester l = new ListenerTester(this);
        NotificationQueue.getInstance().addListener(l);

        NotificationQueue.getInstance().addTimeslot(new NotificationEntry(0, new Date(), "hi"));
        assertTrue(calledTimes <= 1);
        calledTimes = 0;
        NotificationQueue.getInstance().removeListener(l);
        NotificationQueue.getInstance().getTimeslots().clear();
    }

    @Test
    public void addListenerDuplicate() {
        calledTimes = 0;
        // add the same observer twice also will only call once
        ListenerTester l2 = new ListenerTester(this);
        NotificationQueue.getInstance().addListener(l2);
        NotificationQueue.getInstance().addListener(l2);
        NotificationQueue.getInstance().addTimeslot(new NotificationEntry(0, new Date(), "hi"));
        assertTrue(calledTimes == 1);
        calledTimes = 0;
        NotificationQueue.getInstance().removeListener(l2);
        NotificationQueue.getInstance().getTimeslots().clear();
    }

    @Test
    public void addListenerMultithread() {
        // add the same observer twice also will only call once
        ListenerTester l1 = new ListenerTester(this);
        ListenerTester l2 = new ListenerTester(this);
        ListenerTester l3 = new ListenerTester(this);
        ListenerTester l4 = new ListenerTester(this);
        ListenerTester l5 = new ListenerTester(this);
        ListenerTester l6 = new ListenerTester(this);

        Thread t1 = new Thread(() -> {
            NotificationQueue.getInstance().addListener(l1);
        });
        Thread t2 = new Thread(() -> {
            NotificationQueue.getInstance().addListener(l2);
        });
        Thread t3 = new Thread(() -> {
            NotificationQueue.getInstance().addListener(l3);
        });
        Thread t4 = new Thread(() -> {
            NotificationQueue.getInstance().addListener(l4);
        });
        Thread t5 = new Thread(() -> {
            NotificationQueue.getInstance().addListener(l5);
        });
        Thread t6 = new Thread(() -> {
            NotificationQueue.getInstance().addListener(l6);
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
            t6.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        NotificationQueue.getInstance().addTimeslot(new NotificationEntry(0, new Date(), "hi"));
        assertTrue(calledTimes == 6);

        calledTimes = 0;
        NotificationQueue.getInstance().removeListener(l1);
        NotificationQueue.getInstance().removeListener(l2);
        NotificationQueue.getInstance().removeListener(l3);
        NotificationQueue.getInstance().removeListener(l4);
        NotificationQueue.getInstance().removeListener(l5);
        NotificationQueue.getInstance().removeListener(l6);

        NotificationQueue.getInstance().getTimeslots().clear();
    }
}

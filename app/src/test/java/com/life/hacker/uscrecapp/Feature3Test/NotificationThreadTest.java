package com.life.hacker.uscrecapp.Feature3Test;

import com.life.hacker.uscrecapp.SessionData;
import com.life.hacker.uscrecapp.network.NotificationCenter;

import org.junit.Test;
import static org.junit.Assert.*;


public class NotificationThreadTest {
    @Test
    public void startSendNotificationRequest() {
        SessionData.getInstance().clearSession();
        SessionData.getInstance().setToken("1111");

        NotificationCenter.GetInstance().Start();
        assertTrue(NotificationCenter.GetInstance().IsStart());
        NotificationCenter.GetInstance().Stop();
    }

    @Test
    public void stopSendNotificationRequest() {
        SessionData.getInstance().clearSession();
        SessionData.getInstance().setToken("1111");

        NotificationCenter.GetInstance().Start();
        NotificationCenter.GetInstance().Stop();
        assertFalse(NotificationCenter.GetInstance().IsStart());
    }
}

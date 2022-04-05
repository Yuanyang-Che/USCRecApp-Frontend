package com.life.hacker.uscrecapp.Feature3Test;

import org.junit.Test;

import static org.junit.Assert.*;

import com.life.hacker.uscrecapp.Notification.NotificationEntry;
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
public class NotificationTest {
    @Test
    public void notificationAddTimeslotTest() {
        Date d = new Date();

        NotificationQueue.getInstance().addTimeslot(new NotificationEntry(0, d, "hi"));
        NotificationQueue.getInstance().addTimeslot(new NotificationEntry(1, d, "hi"));
        NotificationQueue.getInstance().addTimeslot(new NotificationEntry(2, d, "hi"));
        NotificationQueue.getInstance().addTimeslot(new NotificationEntry(3, d, "hi"));
        NotificationQueue.getInstance().addTimeslot(new NotificationEntry(4, d, "hi"));
        NotificationQueue.getInstance().addTimeslot(new NotificationEntry(5, d, "hi"));

        // test
        assertEquals(6, NotificationQueue.getInstance().getTimeslots().size());

        Object list[] = NotificationQueue.getInstance().getTimeslots().toArray();
        assertEquals(list[0], new NotificationEntry(0, d, "hi"));
        assertEquals(list[1], new NotificationEntry(1, d, "hi"));
        assertEquals(list[2], new NotificationEntry(2, d, "hi"));
        assertEquals(list[3], new NotificationEntry(3, d, "hi"));
        assertEquals(list[4], new NotificationEntry(4, d, "hi"));
        assertEquals(list[5], new NotificationEntry(5, d, "hi"));
        // since it is singleton, we need to clear up the array
        NotificationQueue.getInstance().getTimeslots().clear();
    }

    @Test
    public void notificationAddTimeslotTestDuplicate() {
        Date d = new Date();
        Date d2 = null;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try {
            d2 = format.parse("2008-08-08");
        } catch (Exception e) {
            e.printStackTrace();
        }

        NotificationQueue.getInstance().addTimeslot(new NotificationEntry(0, d, "hi"));
        NotificationQueue.getInstance().addTimeslot(new NotificationEntry(0, d, "hi"));
        NotificationQueue.getInstance().addTimeslot(new NotificationEntry(0, d, "hi"));
        NotificationQueue.getInstance().addTimeslot(new NotificationEntry(3, d, "hi"));
        NotificationQueue.getInstance().addTimeslot(new NotificationEntry(3, d, "hi"));
        NotificationQueue.getInstance().addTimeslot(new NotificationEntry(3, d, "hi, there"));
        NotificationQueue.getInstance().addTimeslot(new NotificationEntry(3, d2, "hi"));
        NotificationQueue.getInstance().addTimeslot(new NotificationEntry(5, d2, "hi"));
        NotificationQueue.getInstance().addTimeslot(new NotificationEntry(5, d, "hi, there"));



        // test. Only insert non duplicate value
        assertEquals(6, NotificationQueue.getInstance().getTimeslots().size());

        Object list[] = NotificationQueue.getInstance().getTimeslots().toArray();
        assertEquals(list[0], new NotificationEntry(0, d, "hi"));
        assertEquals(list[1], new NotificationEntry(3, d, "hi"));
        assertEquals(list[2], new NotificationEntry(3, d, "hi, there"));
        assertEquals(list[3], new NotificationEntry(3, d2, "hi"));
        assertEquals(list[4], new NotificationEntry(5, d2, "hi"));
        assertEquals(list[5], new NotificationEntry(5, d, "hi"));

        // since it is singleton, we need to clear up the array
        NotificationQueue.getInstance().getTimeslots().clear();
    }

    @Test
    public void notificationGetClearTimeslotTest() {
        Date d = new Date();

        NotificationQueue.getInstance().addTimeslot(new NotificationEntry(0, d, "hi"));
        NotificationQueue.getInstance().addTimeslot(new NotificationEntry(1, d, "hi"));
        NotificationQueue.getInstance().addTimeslot(new NotificationEntry(2, d, "hi"));
        NotificationQueue.getInstance().addTimeslot(new NotificationEntry(3, d, "hi"));
        NotificationQueue.getInstance().addTimeslot(new NotificationEntry(4, d, "hi"));
        NotificationQueue.getInstance().addTimeslot(new NotificationEntry(5, d, "hi"));

        // test
        assertEquals(6, NotificationQueue.getInstance().getAndClearTimeslots().size());
        assertEquals(0, NotificationQueue.getInstance().getTimeslots().size());

        // since it is singleton, we need to clear up the array
        NotificationQueue.getInstance().getTimeslots().clear();
    }

}
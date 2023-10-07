package org.example;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class CleaningScheduler {

    private int daysSinceLastClean = 0;
    private ReminderService reminderService;

    public CleaningScheduler(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    public void startScheduler(int frequency) {
        Timer timer = new Timer();
        TimerClass timerClass = new TimerClass();

        // Calculate time until next 24 hour interval
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startTime = calendar.getTime();
        long delay = startTime.getTime() - System.currentTimeMillis();

        timer.schedule(timerClass, delay, 24 * 60 * 60 * 1000);

    }

    public class TimerClass extends TimerTask {
        @Override
        public void run() {
            daysSinceLastClean++;
            if (daysSinceLastClean >= 8) {
                reminderService.remindMember();
            }
        }
    }
}

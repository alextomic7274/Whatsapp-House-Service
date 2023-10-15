package org.example;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class CleaningScheduler {

    private int daysSinceLastClean = 0;
    private int cleanFrequency = 0;
    private ReminderService reminderService;

    public CleaningScheduler(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    public void startScheduler(int frequency) {
        cleanFrequency = frequency;
        Timer timer = new Timer();
        timerTask timerTask = new timerTask();

        // Calculate time until next 24 hour interval
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startTime = calendar.getTime();
        long delay = startTime.getTime() - System.currentTimeMillis();

        //timer.schedule(timerTask, delay, 24 * 60 * 60 * 1000);
        timer.schedule(timerTask, 0, 30 * 1000);


    }

    public class timerTask extends TimerTask {
        @Override
        public void run() {
            daysSinceLastClean++;
            if (daysSinceLastClean >= cleanFrequency) {
                reminderService.remindMember();
            }
        }
    }

    public void resetScheduler() {
        daysSinceLastClean = 0;
    }
}

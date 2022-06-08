package com.greendays.greendays.service.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

public class AnuallyReportScheduler {
    @Scheduled(cron = "0 0 8 3 * *")
    public void scheduleFixedDelayTask() {
        System.out.println(
                "Fixed delay task - " + System.currentTimeMillis() / 1000);
    }
}

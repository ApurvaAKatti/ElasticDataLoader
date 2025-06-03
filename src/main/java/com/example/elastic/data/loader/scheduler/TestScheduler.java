package com.example.elastic.data.loader.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TestScheduler {

    @Scheduled(fixedRate = 5000)
    public void test() {
        System.out.println(">>> Test scheduler is running: " + new java.util.Date());
    }
}
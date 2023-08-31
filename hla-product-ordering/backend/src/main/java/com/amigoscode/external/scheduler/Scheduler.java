package com.amigoscode.external.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Log
public class Scheduler {

//    private final TemperatureService temperatureService;
//    private static final Logger log = LoggerFactory.getLogger(SimpleScheduler.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
//        temperatureService.measureTemperatureAndRecordResult();
        log.info("The time is now " + dateFormat.format(new Date()));
    }
}

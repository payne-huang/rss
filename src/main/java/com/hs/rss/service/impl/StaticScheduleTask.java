package com.hs.rss.service.impl;

import com.hs.rss.service.RssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * description.
 *
 * @author sheng.huang
 * @date 7/8/2019 15:11
 * @since V1.0
 */
@Component
@Configuration
@Slf4j
@EnableScheduling
public class StaticScheduleTask {

    @Autowired
    RssService rssService;

    @Scheduled(cron = "0 25 * * * *")
    private void configureTasks() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("execute the job,current time={}", sdf.format(new Date()));
        rssService.rss();
    }
}

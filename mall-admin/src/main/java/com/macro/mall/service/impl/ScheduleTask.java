package com.macro.mall.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ScheduleTask {
    @Autowired
    private ThirdPlatformWxService thirdPlatformWxService;
    @Scheduled(cron = "0 0 0/2 * * ? ")
    public void refreshToken(){
        log.info("定时任务触发，刷新token");
        thirdPlatformWxService.refreshNewAccessToken();
    }
}

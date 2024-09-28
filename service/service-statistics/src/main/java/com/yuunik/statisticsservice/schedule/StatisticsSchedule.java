package com.yuunik.statisticsservice.schedule;

import com.yuunik.statisticsservice.service.StatisticsDailyService;
import com.yuunik.statisticsservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 每日定时任务
 */
@Component
public class StatisticsSchedule {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    // 每日凌晨4点进行前一天的网站数据统计
    @Scheduled(cron = "* * 4 * * ?")
    public void statisticsByDay() {
        statisticsDailyService.createStatisticsByDate(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }
}

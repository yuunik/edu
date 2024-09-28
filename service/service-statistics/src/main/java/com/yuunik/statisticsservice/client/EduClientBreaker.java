package com.yuunik.statisticsservice.client;

import com.yuunik.baseserive.exception.YuunikException;
import org.springframework.stereotype.Component;

@Component
public class EduClientBreaker implements EduClient {
    @Override
    public int getNumberAddCourse(String date) {
        // 抛出异常
        throw new YuunikException(20001, "获取当日新增课程数失败!");
    }
}

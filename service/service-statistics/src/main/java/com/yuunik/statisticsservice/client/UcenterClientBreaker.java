package com.yuunik.statisticsservice.client;

import com.yuunik.baseserive.exception.YuunikException;
import org.springframework.stereotype.Component;

@Component
public class UcenterClientBreaker implements UcenterClient{
    @Override
    public int getNumberRegistered(String date) {
        // 抛出异常
        throw new YuunikException(20001, "获取当日注册用户数失败!");
    }
}

package com.yuunik.eduservice.client;

import com.yuunik.baseserive.exception.YuunikException;
import org.springframework.stereotype.Component;

@Component
public class OrderFileDegradeClient implements OrderClient {
    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        // 抛出异常
        throw new YuunikException(20001, "查询课程是否购买失败!");
    }
}

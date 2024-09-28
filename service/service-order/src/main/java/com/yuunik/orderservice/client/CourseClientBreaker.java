package com.yuunik.orderservice.client;

import com.yuunik.baseserive.exception.YuunikException;
import com.yuunik.utilscommon.orderVo.CourseWebVo;
import org.springframework.stereotype.Component;

@Component
public class CourseClientBreaker implements CourseClient{
    @Override
    public CourseWebVo getCourseInfoWeb(String id) {
        // 抛出异常
        throw new YuunikException(20001, "获取订单所需的课程信息失败!");
    }
}

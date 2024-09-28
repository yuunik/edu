package com.yuunik.orderservice.client;

import com.yuunik.baseserive.exception.YuunikException;
import com.yuunik.utilscommon.orderVo.MemberWebVo;
import org.springframework.stereotype.Component;

@Component
public class UcenterClientBreaker implements UcenterClient{

    @Override
    public MemberWebVo getUserInfoWeb(String id) {
        // 抛出异常
        throw new YuunikException(20001, "获取订单所需的会员信息失败");
    }
}

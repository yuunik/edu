package com.yuunik.orderservice.service;

import com.yuunik.orderservice.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author yuunik
 * @since 2024-09-24
 */
public interface PayLogService extends IService<PayLog> {

    Map<String, Object> createNative(String orderNo);

    Map<String, String> queryPaylogStatus(String orderNo);

    void updateOrderStatus(Map<String, String> payResult);
}

package com.yuunik.orderservice.service;

import com.yuunik.orderservice.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author yuunik
 * @since 2024-09-24
 */
public interface OrderService extends IService<Order> {

    String addOrderByCourseId(String courseId, int payType, HttpServletRequest request);

    Order getOrderInfo(String orderNo);

    boolean isBuyCourse(String courseId, String memberId);
}

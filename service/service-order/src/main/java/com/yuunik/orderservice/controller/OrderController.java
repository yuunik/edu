package com.yuunik.orderservice.controller;


import com.yuunik.orderservice.entity.Order;
import com.yuunik.orderservice.service.OrderService;
import com.yuunik.utilscommon.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author yuunik
 * @since 2024-09-24
 */
@Api(description = "订单管理接口")
@RestController
@RequestMapping("/orderservice/order")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;

    @ApiOperation("新增订单")
    @PostMapping("/addOrder/{courseId}/{payType}")
    public R addOrder(@ApiParam(name = "courseId", value = "课程id", required = true) @PathVariable String courseId,
                      @ApiParam(name = "payType", value = "支付类型", required = true) @PathVariable int payType,
                      @ApiParam(name = "request", value = "请求对象", required = true) HttpServletRequest request) {
        String orderNo = orderService.addOrderByCourseId(courseId, payType, request);
        return R.ok().data("orderNo", orderNo);
    }

    @ApiOperation("根据订单号查询订单信息")
    @GetMapping("/getOrderInfo/{orderNo}")
    public R getOrderInfo(@ApiParam(name = "orderNo", value = "订单号", required = true) @PathVariable String orderNo) {
        Order order = orderService.getOrderInfo(orderNo);
        return R.ok().data("order", order);
    }
}


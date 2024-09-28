package com.yuunik.orderservice.controller;


import com.yuunik.orderservice.service.PayLogService;
import com.yuunik.utilscommon.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author yuunik
 * @since 2024-09-24
 */
@Api(description = "支付日志接口")
@RestController
@RequestMapping("/orderservice/payLog")
public class PayLogController {
    @Autowired
    private PayLogService payLogService;

    @ApiOperation("生成微信支付二维码")
    @GetMapping("/createNative/{orderNo}")
    public R createNative(@ApiParam(name = "orderNo", value = "订单号", required = true) @PathVariable String orderNo) {
        Map<String, Object> map = payLogService.createNative(orderNo);
        return R.ok().data(map);
    }

    @ApiOperation("查询微信支付状态")
    @GetMapping("/queryWeChatPayStatus/{orderNo}")
    public R queryWeChatPayStatus(@ApiParam(name = "orderNo", value = "订单号", required = true) @PathVariable String orderNo) {
        Map<String, String> payResult = payLogService.queryPaylogStatus(orderNo);
        System.out.println("返回结果 ---> " + payResult);
        if (payResult == null) {
            // 支付失败
            return R.error().message("支付失败, 请稍后再试...");
        }
        if ("SUCCESS".equals(payResult.get("trade_state"))) {
            // 支付成功, 更新订单支付状态, 并生成支付日志
            payLogService.updateOrderStatus(payResult);
            return R.ok().message("支付成功");
        }
        return R.ok().code(1997).message("支付中...");
    }

}


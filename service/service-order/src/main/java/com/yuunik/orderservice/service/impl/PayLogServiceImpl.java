package com.yuunik.orderservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.yuunik.baseserive.exception.YuunikException;
import com.yuunik.orderservice.entity.Order;
import com.yuunik.orderservice.entity.PayLog;
import com.yuunik.orderservice.mapper.PayLogMapper;
import com.yuunik.orderservice.service.OrderService;
import com.yuunik.orderservice.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuunik.orderservice.utils.HttpClient;
import com.yuunik.orderservice.utils.WeChatPayConstantsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author yuunik
 * @since 2024-09-24
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    @Autowired
    private OrderService orderService;

    // 生成微信支付二维码
    @Override
    public Map<String, Object> createNative(String orderNo) {
        // 获取订单信息
        LambdaQueryWrapper<Order> wrapper = new QueryWrapper<Order>().lambda();
        wrapper.eq(Order::getOrderNo, orderNo);
        // 调用接口, 获取订单信息
        Order order = orderService.getOne(wrapper);
        if (order == null) {
            // 抛出异常
            throw new YuunikException(20001, "订单不存在");
        }
        // 设置支付参数
        Map<String, String> paramsMap = new HashMap<>();
        // 公众号 appId
        paramsMap.put("appid", WeChatPayConstantsUtil.APP_ID);
        // 商户号
        paramsMap.put("mch_id", WeChatPayConstantsUtil.PARTNER_ID);
        // 随机字符串
        paramsMap.put("nonce_str", WXPayUtil.generateNonceStr());
        paramsMap.put("body", order.getCourseTitle());
        // 订单号
        paramsMap.put("out_trade_no", orderNo);
        // 金额
        paramsMap.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue() + "");
        // 创建支付的地址
        paramsMap.put("spbill_create_ip", "127.0.0.1");
        // 回调地址
        paramsMap.put("notify_url", WeChatPayConstantsUtil.NOTIFY_URL);
        // 支付类型, NATIVE 为由金额生成支付二维码
        paramsMap.put("trade_type", "NATIVE");

        try {
            // 根据 HttpClient 来访问微信支付第三方接口, 并且传递参数
            HttpClient client = new HttpClient(WeChatPayConstantsUtil.DOMAIN);
            // 设置参数
            client.setXmlParam(WXPayUtil.generateSignedXml(paramsMap, WeChatPayConstantsUtil.PARTNER_KEY));
            // 发送请求
            client.post();

            // 获取返回结果
            String xmlConent = client.getContent();
            // xml 转换为 map
            Map<String, String> wxPayResult = WXPayUtil.xmlToMap(xmlConent);

            // 根据响应结果, 封装响应数据
            Map<String, Object> result = new HashMap<>();
            result.put("out_trade_no", orderNo);
            result.put("course_id", order.getCourseId());
            result.put("total_fee", order.getTotalFee());
            result.put("result_code", wxPayResult.get("result_code"));
            result.put("code_url", wxPayResult.get("code_url"));

            return result;
        } catch (Exception e) {
            // 输出异常信息
            e.printStackTrace();
            throw  new YuunikException(20001, "获取微信二维码失败");
        }
    }

    // 查询微信支付状态
    @Override
    public Map<String, String> queryPaylogStatus(String orderNo) {
        // 设置参数
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("appid", WeChatPayConstantsUtil.APP_ID);
        paramsMap.put("mch_id", WeChatPayConstantsUtil.PARTNER_ID);
        paramsMap.put("out_trade_no", orderNo);
        paramsMap.put("nonce_str", WXPayUtil.generateNonceStr());

        try {
            // 访问微信接口
            HttpClient client = new HttpClient(WeChatPayConstantsUtil.QUERY_ORDER_DOMAIN);
            // 设置参数
            client.setXmlParam(WXPayUtil.generateSignedXml(paramsMap, WeChatPayConstantsUtil.PARTNER_KEY));
            // 发送请求
            client.post();

            // 获取返回结果
            String xmlContent = client.getContent();
            Map<String, String> result = WXPayUtil.xmlToMap(xmlContent);
            return result;
        } catch (Exception e) {
            // 输出异常信息
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // 更新订单状态, 生成支付日志
    @Override
    public void updateOrderStatus(Map<String, String> payResult) {
        // 条件
        LambdaQueryWrapper<Order> wrapper = new QueryWrapper<Order>().lambda();
        wrapper.eq(Order::getOrderNo, payResult.get("out_trade_no"));
        Order order = orderService.getOne(wrapper);
        if (order == null) {
            // 抛出异常
            throw new YuunikException(20001, "订单不存在");
        }
        if (order.getStatus().intValue() == 1) {
            // 订单已支付, 不需要更新
            return;
        }
        // 修改支付状态
        order.setStatus(1);
        // 调用接口, 更新订单支付状态
        boolean isUpdate = orderService.updateById(order);
        if (!isUpdate) {
            throw new YuunikException(20001, "更新订单状态失败");
        }
        // 生成支付日志
        PayLog payLog = new PayLog();
        payLog.setOrderNo(payResult.get("out_trade_no"));
        payLog.setPayTime(new Date());
        payLog.setTotalFee(order.getTotalFee());
        payLog.setTransactionId(payResult.get("transaction_id"));
        payLog.setTradeState(payResult.get("trade_state"));
        payLog.setAttr(JSON.toJSONString(payResult));
        // 调用接口, 新增支付日志
        boolean isAdd = this.save(payLog);
        if (!isAdd) {
            throw new YuunikException(20001, "生成支付日志失败");
        }

    }
}

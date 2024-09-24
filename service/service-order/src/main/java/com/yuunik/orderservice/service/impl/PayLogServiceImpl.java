package com.yuunik.orderservice.service.impl;

import com.yuunik.orderservice.entity.PayLog;
import com.yuunik.orderservice.mapper.PayLogMapper;
import com.yuunik.orderservice.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}

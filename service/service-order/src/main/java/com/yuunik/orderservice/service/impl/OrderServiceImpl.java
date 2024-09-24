package com.yuunik.orderservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuunik.baseserive.exception.YuunikException;
import com.yuunik.orderservice.client.CourseClient;
import com.yuunik.orderservice.client.UcenterClient;
import com.yuunik.orderservice.entity.Order;
import com.yuunik.orderservice.mapper.OrderMapper;
import com.yuunik.orderservice.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuunik.orderservice.utils.OrderNoUtil;
import com.yuunik.utilscommon.utils.JwtUtil;
import com.yuunik.utilscommon.orderVo.CourseWebVo;
import com.yuunik.utilscommon.orderVo.MemberWebVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author yuunik
 * @since 2024-09-24
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private CourseClient courseClient;

    @Autowired
    private UcenterClient ucenterClient;

    // 新增订单
    @Override
    public String addOrderByCourseId(String courseId, int payType, HttpServletRequest request) {
        // 非空判断
        if (StringUtils.isEmpty(courseId)) {
            // 抛出异常
            throw new YuunikException(20001, "课程 id 不能为空!");
        }
        // token 有效性校验
        boolean isOk = JwtUtil.checkToken(request);
        if(!isOk) {
            // token 失效
            throw new YuunikException(20001, "token 失效!");
        }
        // 获取用户 id
        String memberId = JwtUtil.getMemberIdByJwtToken(request);
        // 重复提交校验
        LambdaQueryWrapper<Order> wrapper = new QueryWrapper<Order>().lambda();
        wrapper.eq(Order::getCourseId, courseId).eq(Order::getMemberId, memberId);
        // 调用接口, 查询本次生成的订单是否已存在
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            // 重复提交
            throw new YuunikException(20001, "请勿重复提交!");
        }
        // 服务调用, 获取所需的课程信息
        CourseWebVo courseInfo = courseClient.getCourseInfoWeb(courseId);
        // 服务调用, 获取所需的用户信息
        MemberWebVo userInfo = ucenterClient.getUserInfoWeb(memberId);
        // 根据响应结果, 封装数据
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseInfo.getId());
        order.setCourseTitle(courseInfo.getTitle());
        order.setCourseCover(courseInfo.getCover());
        order.setTeacherName(courseInfo.getTeacherName());
        order.setMemberId(userInfo.getId());
        order.setNickname(userInfo.getNickname());
        order.setMobile(userInfo.getMobile());
        order.setTotalFee(courseInfo.getPrice());
        order.setPayType(payType);
        order.setStatus(0);
        // 调用接口, 生成订单
        boolean result = this.save(order);
        if (!result) {
            // 抛出异常
            throw new YuunikException(20001, "生成订单失败!");
        }
        // 返回订单号
        return order.getOrderNo();
    }

    // 获取订单详情
    @Override
    public Order getOrderInfo(String orderNo) {
        LambdaQueryWrapper<Order> wrapper = new QueryWrapper<Order>().lambda();
        wrapper.eq(Order::getOrderNo, orderNo);
        // 调用接口, 获取订单详情
        Order order = this.getOne(wrapper);
        if (order == null) {
            throw new YuunikException(20001, "订单不存在!");
        }
        return order;
    }
}

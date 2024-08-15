package com.yuunik.ucenterservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuunik.baseserive.exception.YuunikException;
import com.yuunik.ucenterservice.entity.UcenterMember;
import com.yuunik.ucenterservice.entity.vo.RegisterInfoVo;
import com.yuunik.ucenterservice.mapper.UcenterMemberMapper;
import com.yuunik.ucenterservice.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuunik.utilscommon.JwtUtil;
import com.yuunik.utilscommon.MD5;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author yuunik
 * @since 2024-08-15
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 用户登录
    @Override
    public String login(UcenterMember ucenterMember) {
        // 获取用户信息
        String mobile = ucenterMember.getMobile();
        String nickname = ucenterMember.getNickname();
        String password = ucenterMember.getPassword();

        // 非空判断
        if (StringUtils.isEmpty(mobile) && StringUtils.isEmpty(nickname)) {
            // 抛出异常
            throw new YuunikException(20001, "用户名/手机号不能为空");
        }

        if (StringUtils.isEmpty(password)) {
            // 抛出异常
            throw new YuunikException(20001, "密码不能为空");
        }

        // 默认手机号登录, 若没填手机号, 则为用户名登录
        LambdaQueryWrapper<UcenterMember> wrapper = null;
        UcenterMember user = null;
        // 查找用户是否存在
        if (StringUtils.isEmpty(mobile)) {
            wrapper = new QueryWrapper<UcenterMember>().lambda();
            wrapper.eq(UcenterMember::getNickname, nickname);
            // 调用接口, 查询用户是否存在
            user = this.getOne(wrapper);
            if (user == null) {
                // 抛出异常
                throw new YuunikException(20001, "用户名不存在");
            }
        } else {
            wrapper = new QueryWrapper<UcenterMember>().lambda();
            wrapper.eq(UcenterMember::getMobile, mobile);
            // 调用接口, 查询用户是否存在
            user = this.getOne(wrapper);
            if (user == null) {
                // 抛出异常
                throw new YuunikException(20001, "手机号不存在");
            }
        }

        // 密码核验
        if (!MD5.encrypt(password).equals(user.getPassword())) {
            // 抛出异常
            throw new YuunikException(20001, "密码错误");
        }

        // 根据 jwt 工具类, 生成用户 token
        String token = JwtUtil.getJwtToken(user.getId(), user.getNickname());
        return token;
    }

    // 注册用户
    @Override
    public void registerUser(RegisterInfoVo registerInfo) {
        // 获取注册信息
        String mobile = registerInfo.getMobile();
        String nickname = registerInfo.getNickname();
        String password = registerInfo.getPassword();
        String code = registerInfo.getCode();

        // 非空判断
        if (StringUtils.isEmpty(mobile)) {
            // 抛出异常
            throw new YuunikException(20001, "注册手机号不能为空");
        }

        if (StringUtils.isEmpty(code)) {
            // 抛出异常
            throw new YuunikException(20001, "注册验证码不能为空");
        }

        // 注册验证码核验
        String registerCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(registerCode)) {
            // 抛出异常
            throw new YuunikException(20001, "验证码错误");
        }

        // 其余信息非空判断
        if (StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password)) {
            // 抛出异常
            throw new YuunikException(20001, "昵称/密码不能为空");
        }

        // 注册用户是否存在
        LambdaQueryWrapper<UcenterMember> wrapper = new QueryWrapper<UcenterMember>().lambda();
        wrapper.eq(UcenterMember::getMobile, mobile);
        // 调用接口, 查找用户
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            // 注册手机号码已存在
            throw new YuunikException(20001, "注册手机号码已存在");
        }

        // 密码加密
        registerInfo.setPassword(MD5.encrypt(password));
        // 添加用户
        UcenterMember user = new UcenterMember();
        BeanUtils.copyProperties(registerInfo, user);

        // 调用接口, 注册用户
        boolean isSuccess = this.save(user);
        if (!isSuccess) {
            // 抛出异常
            throw new YuunikException(20001, "注册失败");
        }
    }

    // 根据 token, 获取用户信息
    @Override
    public UcenterMember checkToken(HttpServletRequest request) {
        // 检查 token 的有效性
        boolean isVaild = JwtUtil.checkToken(request);
        if (!isVaild) {
            // 抛出异常
            throw new YuunikException(20001, "token 无效");
        }
        // 获取用户 id
        String id = JwtUtil.getMemberIdByJwtToken(request);
        // 根据用户 id, 查询用户信息
        LambdaQueryWrapper<UcenterMember> wrapper = new QueryWrapper<UcenterMember>().lambda();
        wrapper.eq(UcenterMember::getId, id);
        UcenterMember userInfo = this.getById(id);
        // 判断用户信息
        if (userInfo == null) {
            // 抛出异常
            throw new YuunikException(20001, "用户信息不存在");
        }
        return userInfo;
    }
}

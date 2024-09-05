package com.yuunik.ucenterservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.yuunik.baseserive.exception.YuunikException;
import com.yuunik.ucenterservice.entity.UcenterMember;
import com.yuunik.ucenterservice.entity.vo.RegisterInfoVo;
import com.yuunik.ucenterservice.mapper.UcenterMemberMapper;
import com.yuunik.ucenterservice.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuunik.ucenterservice.utils.HttpClientUtils;
import com.yuunik.ucenterservice.utils.WechatConstantUtil;
import com.yuunik.utilscommon.JwtUtil;
import com.yuunik.utilscommon.MD5;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

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

    private static final Logger log = LoggerFactory.getLogger(UcenterMemberServiceImpl.class);
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

    // 微信登录
    @Override
    public String loginByWechat(String code, String state) {
        // 1. 获取code值，临时票据，类似于验证码
        System.out.println("code = " + code);
        // 2. 获取state值，和redis中存储的state值进行比对
        System.out.println("state = " + state);
        // 向认证服务器发送请求换取 access_token 和 openId
        String baseAccessTokenUrl =
                "https://api.weixin.qq.com/sns/oauth2/access_token" +
                        "?appid=%s" +
                        "&secret=%s" +
                        "&code=%s" +
                        "&grant_type=authorization_code";
        // 拼接三个参数
        String accessTokenUrl = String.format(baseAccessTokenUrl, WechatConstantUtil.APP_ID, WechatConstantUtil.APP_SECRET, code);
        // 发送请求, 获取 access_token 和 openid
        String accessResultStr = null;
        try {
            accessResultStr = HttpClientUtils.get(accessTokenUrl);
        } catch (Exception e) {
            throw new YuunikException(20001, "获取用户令牌失败");
        }
        // 将结果转换为 map
        HashMap accessMap = new Gson().fromJson(accessResultStr, HashMap.class);
        // 获取access_token
        String accessToken = (String) accessMap.get("access_token");
        // 获取 openId
        String openid = (String) accessMap.get("openid");

        // 调用接口, 查询用户是否已存在
        LambdaQueryWrapper<UcenterMember> wrapper = new QueryWrapper<UcenterMember>().lambda();
        wrapper.eq(UcenterMember::getOpenid, openid);
        UcenterMember user = this.getOne(wrapper);

        // 判断用户是否存在, 存在则返回
        if (user != null) {
            String token = JwtUtil.getJwtToken(user.getId(), user.getNickname());
            return token;
        }

        //访问微信的资源服务器，获取用户信息
        String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                "?access_token=%s" +
                "&openid=%s";
        // 拼接参数
        String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
        // 发送请求, 获取用户信息
        String userInfoStr = null;
        try {
            userInfoStr = HttpClientUtils.get(userInfoUrl);
        } catch (Exception e) {
            throw new YuunikException(20001, "获取用户信息失败");
        }
        // 转化为 map
        HashMap userInfoMap = new Gson().fromJson(userInfoStr, HashMap.class);
        // 获取用户信息
        String nickname = (String) userInfoMap.get("nickname");
        // Object 转换为 int
        Double sexDouble = (Double) userInfoMap.get("sex");
        int sex = sexDouble.intValue();
        String avatar = (String) userInfoMap.get("headimgurl");
        // 注册用户
        UcenterMember wechatUser = new UcenterMember();
        wechatUser.setOpenid(openid);
        wechatUser.setNickname(nickname);
        wechatUser.setAvatar(avatar);
        wechatUser.setSex(sex);
        int insert = baseMapper.insert(wechatUser);
        if (insert < 1) {
            throw new YuunikException(20001, "注册微信用户失败");
        }
        // 根据 jwt 工具类, 生成用户令牌 token
        String token = JwtUtil.getJwtToken(wechatUser.getId(), wechatUser.getNickname());

        return token;
    }
}

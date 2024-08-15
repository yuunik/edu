package com.yuunik.ucenterservice.service;

import com.yuunik.ucenterservice.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yuunik.ucenterservice.entity.vo.RegisterInfoVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author yuunik
 * @since 2024-08-15
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember ucenterMember);

    void registerUser(RegisterInfoVo registerInfo);
}

package com.yuunik.statisticsservice.service;

import com.yuunik.statisticsservice.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author yuunik
 * @since 2024-09-29
 */
public interface UcenterMemberService extends IService<UcenterMember> {
    int getNumberRegistered(String date);
}

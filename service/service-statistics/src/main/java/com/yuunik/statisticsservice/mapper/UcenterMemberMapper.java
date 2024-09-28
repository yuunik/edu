package com.yuunik.statisticsservice.mapper;

import com.yuunik.statisticsservice.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author yuunik
 * @since 2024-09-29
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {
    // 获取当日的注册人数
    int selectNumberRegistered(String date);
}

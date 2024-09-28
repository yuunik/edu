package com.yuunik.ucenterservice.mapper;

import com.yuunik.ucenterservice.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author yuunik
 * @since 2024-08-15
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    // 查询某一天的注册人数
    int selectNumberRegistered(String date);
}

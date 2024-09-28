package com.yuunik.statisticsservice.service.impl;

import com.yuunik.statisticsservice.entity.UcenterMember;
import com.yuunik.statisticsservice.mapper.UcenterMemberMapper;
import com.yuunik.statisticsservice.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author yuunik
 * @since 2024-09-29
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    // 获取当日的注册人数
    @Override
    public int getNumberRegistered(String date) {
        return baseMapper.selectNumberRegistered(date);
    }
}

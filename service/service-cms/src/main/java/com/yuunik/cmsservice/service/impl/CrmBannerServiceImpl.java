package com.yuunik.cmsservice.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuunik.baseserive.exception.YuunikException;
import com.yuunik.cmsservice.entity.CrmBanner;
import com.yuunik.cmsservice.mapper.CrmBannerMapper;
import com.yuunik.cmsservice.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuunik.utilscommon.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author yuunik
 * @since 2024-08-13
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    private static final Logger log = LoggerFactory.getLogger(CrmBannerServiceImpl.class);

    // 分页查询全部的轮播图
    @Override
    public R pageAllBanner(long current, long pageSize) {
        Page<CrmBanner> page = new Page<>(current, pageSize);
        // 调用接口, 分页查询轮播图
        this.page(page,null);
        return R.ok().data("records", page.getRecords())
                .data("total", page.getTotal())
                .data("pages",page.getPages())
                .data("current", page.getCurrent())
                .data("size", page.getSize());
    }

    // 添加轮播图
    @Override
    public Boolean addBanner(CrmBanner crmBanner) {
        int result = baseMapper.insert(crmBanner);
        if (result > 0) {
            return true;
        }
        return false;
    }

    // 删除轮播图
    @Override
    public Boolean removeBannerById(String id) {
        return this.removeById(id);
    }

    // 修改轮播图
    @Override
    public Boolean editBannerById(CrmBanner crmBanner) {
        int result = baseMapper.updateById(crmBanner);
        return result > 0;
    }

    // 获取轮播图详情
    @Override
    public CrmBanner getBannerBydId(String id) {
        CrmBanner crmBanner = this.getById(id);
        if (crmBanner == null) {
            throw new YuunikException(20001, "获取轮播图失败");
        }
        return crmBanner;
    }

    // 查询所有轮播图
    @Cacheable(value = "banner", key = "'selectIndexList'")
    @Override
    public List<CrmBanner> getBannerList() {
        return baseMapper.selectList(null);
    }
}

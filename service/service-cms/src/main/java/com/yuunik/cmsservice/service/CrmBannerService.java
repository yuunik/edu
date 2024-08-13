package com.yuunik.cmsservice.service;

import com.yuunik.cmsservice.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yuunik.utilscommon.R;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author yuunik
 * @since 2024-08-13
 */
public interface CrmBannerService extends IService<CrmBanner> {

    R pageAllBanner(long current, long pageSize);

    Boolean addBanner(CrmBanner crmBanner);

    Boolean removeBannerById(String id);

    Boolean editBannerById(CrmBanner crmBanner);

    CrmBanner getBannerBydId(String id);

    List<CrmBanner> getBannerList();
}

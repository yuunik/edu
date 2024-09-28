package com.yuunik.cmsservice.controller;

import com.yuunik.cmsservice.entity.CrmBanner;
import com.yuunik.cmsservice.service.CrmBannerService;
import com.yuunik.utilscommon.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(description = "banner前台查询接口")
@RestController
@RequestMapping("/cmsservice/user")
public class BannerFrontController {
    @Autowired
    private CrmBannerService crmBannerService;

    @ApiOperation("查询轮播图")
    @GetMapping("/getAllBanner")
    public R getBannerList() {
        List<CrmBanner> crmBannerList = crmBannerService.getBannerList();
        return R.ok().data("bannerList", crmBannerList);
    }
}

package com.yuunik.cmsservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuunik.cmsservice.entity.CrmBanner;
import com.yuunik.cmsservice.service.CrmBannerService;
import com.yuunik.utilscommon.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author yuunik
 * @since 2024-08-13
 */
@Api(description = "banner后台管理接口")
@RestController
@RequestMapping("/cmsservice/admin")
public class CrmBannerController {
    @Autowired
    private CrmBannerService crmBannerService;

    @ApiOperation("分页查询轮播图")
    @GetMapping("/pageAllBanner/{current}/{pageSize}")
    public R pageAllBanner(@PathVariable long current, @PathVariable long pageSize) {
        R result = crmBannerService.pageAllBanner(current, pageSize);
        return result;
    }

    @ApiOperation("添加轮播图")
    @PostMapping("/addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner) {
        Boolean result = crmBannerService.addBanner(crmBanner);
        if (!result) {
            return R.error().message("添加轮播图失败");
        }
        return R.ok();
    }

    @ApiOperation("删除轮播图")
    @DeleteMapping("/removeBanner/{id}")
    public R removeBanner(@PathVariable String id) {
        Boolean result = crmBannerService.removeBannerById(id);
        if (!result) {
            return R.error().message("删除轮播图失败");
        }
        return R.ok();
    }

    @ApiOperation("修改轮播图")
    @PostMapping("/editBanner")
    public R editBanner(@RequestBody CrmBanner crmBanner) {
        Boolean result = crmBannerService.editBannerById(crmBanner);
        if (!result) {
            return R.error().message("修改轮播图失败");
        }
        return R.ok();
    }

    @ApiOperation("获取轮播图详情")
    @GetMapping("/getBanner/{id}")
    public R getBanner(@PathVariable String id) {
        CrmBanner crmBanner = crmBannerService.getBannerBydId(id);
        return R.ok().data("banner", crmBanner);
    }
}


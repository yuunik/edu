package com.yuunik.statisticsservice.controller;


import com.yuunik.statisticsservice.entity.vo.ChartVo;
import com.yuunik.statisticsservice.service.StatisticsDailyService;
import com.yuunik.utilscommon.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author yuunik
 * @since 2024-09-26
 */
@Api(description = "网站统计日数据接口")
@RestController
@RequestMapping("/statisticsservice/statistics")
@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    @ApiOperation("统计每日数据")
    @PostMapping("/createStatisticsByDate/{date}")
    public R createStatisticsByDate(@ApiParam(name = "date", value = "统计日期", required = true, example = "2024-09-26") @PathVariable String date) {
        statisticsDailyService.createStatisticsByDate(date);
        return R.ok();
    }

    @ApiOperation("获取图表数据")
    @GetMapping("/getChartData/{statisticsType}/{beginDate}/{endDate}")
    public R getChartData(@ApiParam(name = "statisticsType", value = "统计类型", required = true) @PathVariable String statisticsType,
                          @ApiParam(name = "beginDate", value = "开始日期", required = true) @PathVariable String beginDate,
                          @ApiParam(name = "endDate", value = "结束日期", required = true) @PathVariable String endDate) {
        List<ChartVo> chartData = statisticsDailyService.getChartData(statisticsType, beginDate, endDate);
        return R.ok().data("chartData", chartData);
    }
}


package com.yuunik.statisticsservice.service;

import com.yuunik.statisticsservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yuunik.statisticsservice.entity.vo.ChartVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author yuunik
 * @since 2024-09-26
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void createStatisticsByDate(String date);

    List<ChartVo> getChartData(String statisticsType, String beginDate, String endDate);
}

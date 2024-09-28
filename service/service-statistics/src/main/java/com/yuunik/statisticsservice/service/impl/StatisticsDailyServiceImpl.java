package com.yuunik.statisticsservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuunik.baseserive.exception.YuunikException;
import com.yuunik.statisticsservice.client.EduClient;
import com.yuunik.statisticsservice.client.UcenterClient;
import com.yuunik.statisticsservice.entity.StatisticsDaily;
import com.yuunik.statisticsservice.entity.vo.ChartVo;
import com.yuunik.statisticsservice.mapper.StatisticsDailyMapper;
import com.yuunik.statisticsservice.service.EduCourseService;
import com.yuunik.statisticsservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuunik.statisticsservice.service.UcenterMemberService;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author yuunik
 * @since 2024-09-26
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private UcenterMemberService ucenterMemberService;

    // 统计每日数据
    @Override
    public void createStatisticsByDate(String date) {
        // 查询是否存在旧数据
        LambdaQueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<StatisticsDaily>().lambda();
        wrapper.eq(StatisticsDaily::getDateCalculated, date);
        StatisticsDaily oldData = this.getOne(wrapper);
        if (oldData != null) {
            // 存在旧数据, 则删除
            int isDelete = baseMapper.deleteById(oldData);
            if (isDelete == 0) {
                // 抛出异常
                throw new YuunikException(20001, "删除旧统计数据失败!");
            }
        }
        // 获取当日的注册人数
        int numberRegistered = ucenterMemberService.getNumberRegistered(date);
        // 获取当日的登录人数
        int numberLogin = RandomUtils.nextInt(100, 1000);
        // 获取当日的课程播放数
        int numberPlay = RandomUtils.nextInt(100, 1000);
        // 获取当日的课程数
        int numberAddCourse = eduCourseService.getNumberAddCourse(date);
        // 封装数据
        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setDateCalculated(date);
        statisticsDaily.setRegisterNum(numberRegistered);
        statisticsDaily.setLoginNum(numberLogin);
        statisticsDaily.setVideoViewNum(numberPlay);
        statisticsDaily.setCourseNum(numberAddCourse);
        // 调用接口, 生成当日的统计数据
        boolean isSuccess = this.save(statisticsDaily);
        if (!isSuccess) {
            // 抛出异常
            throw new YuunikException(20001, "生成统计数据失败!");
        }
    }

    // 获取图表数据
    @Override
    public List<ChartVo> getChartData(String statisticsType, String beginDate, String endDate) {
        // 非空校验
        if (StringUtils.isEmpty(statisticsType) || StringUtils.isEmpty(beginDate) || StringUtils.isEmpty(endDate)) {
            // 抛出异常
            throw new YuunikException(20001, "请求参数错误!");
        }
        // 条件
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", beginDate, endDate).select(statisticsType, "date_calculated").orderByAsc("date_calculated");
        // 调用接口, 获取该时间段的统计数据
        List<StatisticsDaily> result = this.list(wrapper);
        // 根据响应信息, 封装数据
        if (result == null) {
            // 抛出异常
            throw new YuunikException(20001, "该时间段无相关数据!");
        }
        List<ChartVo> dataList = new ArrayList<>();
        ChartVo chartVo = null;
        for (StatisticsDaily statisticsDaily : result) {
            chartVo = new ChartVo();
            // 封装日期
            chartVo.setDate(statisticsDaily.getDateCalculated());
            // 判断当前的统计类型
            switch (statisticsType) {
                case "register_num":
                    chartVo.setNum(statisticsDaily.getRegisterNum());
                    break;
                case "login_num":
                    chartVo.setNum(statisticsDaily.getLoginNum());
                    break;
                case "video_view_num":
                    chartVo.setNum(statisticsDaily.getVideoViewNum());
                    break;
                case "course_num":
                    chartVo.setNum(statisticsDaily.getCourseNum());
            }
            dataList.add(chartVo);
        }
        return dataList;
    }
}

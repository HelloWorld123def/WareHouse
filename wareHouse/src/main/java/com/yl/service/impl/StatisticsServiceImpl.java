package com.yl.service.impl;


import com.yl.entity.Statistics;
import com.yl.mapper.StatisticsMapper;
import com.yl.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private StatisticsMapper statisticsMapper;

    @Override
    public List<Statistics> statisticsStoreInvent() {

        List<Statistics> statisticsList = statisticsMapper.statisticsStoreInvent();
        return statisticsList;

    }
}

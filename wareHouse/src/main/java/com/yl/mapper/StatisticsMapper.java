package com.yl.mapper;


import com.yl.entity.Statistics;
import com.yl.entity.Store;
import com.yl.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StatisticsMapper {

    //统计各个仓库商品库存数量的方法
    List<Statistics> statisticsStoreInvent();


}

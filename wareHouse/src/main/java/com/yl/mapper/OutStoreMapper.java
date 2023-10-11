package com.yl.mapper;

import com.yl.entity.OutStore;
import com.yl.page.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutStoreMapper {

    //添加出库单
    int insertOutStore(OutStore outStore);

    //查询出库单总行数的方法
    public int outStoreCount(OutStore outStore);

    //分页查询出库单的方法
    public List<OutStore> outStorePage(@Param("page") Page page, @Param("outStore") OutStore outStore);

    //根据id修改出库单状态
    int setIsOutById(Integer outStoreId);
}

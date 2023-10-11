package com.yl.mapper;

import com.yl.entity.InStore;
import com.yl.page.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Repository
public interface InStoreMapper {

    //查询入库单总行数的方法
    int selectInStoreCount(InStore inStore);


    //分页查询入库单的方法
    public List<InStore> selectInStorePage(@Param("page") Page page, @Param("inStore") InStore inStore);


    //添加入库单方法
    int insertInStore(InStore inStore);

    //根据id将入库单状态改为已入库的方法
    public int updateIsInById(Integer insId);


}

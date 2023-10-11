package com.yl.mapper;

import com.yl.entity.Store;
import com.yl.page.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreMapper {

    //查询所有仓库的方法
    List<Store> findAllStore();

    //查询仓库总行数的方法
    int selectStoreCount(Store store);

    //分页查询仓库的方法
    List<Store> selectStorePage(@Param("page") Page page, @Param("store") Store store);

    //根据仓库编号查询仓库的方法
    Store selectStoreByNum(String storeNum);

    //添加仓库
    int insertStore(Store store);


    //根据仓库id修改仓库的方法
    public int updateStoreById(Store store);

    //根据仓库id删除仓库的方法
    public int deleteStoreById(Integer storeId);
}

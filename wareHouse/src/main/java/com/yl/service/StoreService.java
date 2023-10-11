package com.yl.service;

import com.yl.entity.Result;
import com.yl.entity.Store;
import com.yl.page.Page;

import java.util.List;

public interface StoreService {

    //查询所有仓库的方法
    List<Store> queryAllStore();

    Page queryStorePage(Page page, Store store);


    Result saveStore(Store store);

    Result checkStoreNum(String storeNum);

    Result updateStore(Store store);

    Result deleteStore(Integer storeId);
}

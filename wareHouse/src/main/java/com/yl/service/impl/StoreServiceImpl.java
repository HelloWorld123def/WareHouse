package com.yl.service.impl;

import com.yl.entity.Result;
import com.yl.entity.Store;
import com.yl.mapper.StoreMapper;
import com.yl.page.Page;
import com.yl.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreMapper storeMapper;

    @Override
    public List<Store> queryAllStore() {
        return storeMapper.findAllStore();
    }

    @Override
    public Page queryStorePage(Page page, Store store) {

        int selectStoreCount = storeMapper.selectStoreCount(store);

        List<Store> storeList = storeMapper.selectStorePage(page, store);

        page.setTotalNum(selectStoreCount);

        page.setResultList(storeList);
        return page;
    }



    @Override
    public Result saveStore(Store store) {
        return Result.ok("添加仓库成功",storeMapper.insertStore(store));
    }

    @Override
    public Result checkStoreNum(String storeNum) {

        Store storeByNum = storeMapper.selectStoreByNum(storeNum);
        return Result.ok(storeByNum==null);
    }

    @Override
    public Result updateStore(Store store) {
        return Result.ok(storeMapper.updateStoreById(store));
    }

    @Override
    public Result deleteStore(Integer storeId) {
        return Result.ok(storeMapper.deleteStoreById(storeId));
    }
}

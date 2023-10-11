package com.yl.service.impl;


import com.yl.entity.InStore;
import com.yl.entity.Result;
import com.yl.mapper.InStoreMapper;
import com.yl.mapper.ProductMapper;
import com.yl.mapper.PurchaseMapper;
import com.yl.page.Page;
import com.yl.service.InStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
public class InStoreServiceImpl implements InStoreService {
    @Autowired
    private InStoreMapper inStoreMapper;

    @Autowired
    private PurchaseMapper purchaseMapper;

    @Autowired
    private ProductMapper productMapper;


    @Transactional
    @Override
    public Result saveInStore(InStore inStore,Integer buyId) {
        //添加入库单
        int i = inStoreMapper.insertInStore(inStore);
        if (i>0)
        {
            //修改采购单状态，已入库
            int i1 = purchaseMapper.updateIsInById(buyId);
            if (i1>0){
                return Result.ok("入库单添加成功");
            }
            return Result.err(Result.CODE_ERR_BUSINESS,"入库单添加失败");
        }

        return Result.err(Result.CODE_ERR_BUSINESS,"入库单添加失败");

    }

    @Override
    public Page queryInStorePage(Page page, InStore inStore) {

        int inStoreCount = inStoreMapper.selectInStoreCount(inStore);

        List<InStore> inStoreList = inStoreMapper.selectInStorePage(page, inStore);

        page.setPageCount(inStoreCount);
        page.setResultList(inStoreList);

        return page;
    }


    @Transactional
    @Override
    public Result confirmInStore(InStore inStore) {

        //根据id将入库单状态改为已入库
        int i = inStoreMapper.updateIsInById(inStore.getInsId());
        if (i>0){
            //根据商品id增加商品库存
            int i1 = productMapper.setInventById(inStore.getProductId(), inStore.getInNum());
            if (i1>0){
                return Result.ok("入库成功");
            }
            return Result.err(Result.CODE_ERR_BUSINESS,"入库失败");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"入库失败");
    }

}

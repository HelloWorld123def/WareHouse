package com.yl.service.impl;

import com.yl.entity.Purchase;
import com.yl.entity.Result;
import com.yl.mapper.PurchaseMapper;
import com.yl.page.Page;
import com.yl.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private PurchaseMapper purchaseMapper;

    @Override
    public Result savePurchase(Purchase purchase) {
        //补充字段--给fact_buy_num字段实际采购数量赋值为buy_num字段的预采购数量
        purchase.setFactBuyNum(purchase.getBuyNum());

        int i = purchaseMapper.insertPurchase(purchase);
        if (i>0){
            return Result.ok("添加成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"添加失败");
    }

    @Override
    public Page queryPurchasePage(Page page, Purchase purchase) {

        //查询采购单总行数
        Integer purchaseCount = purchaseMapper.selectPurchaseCount(purchase);

        //分页查询采购单
        List<Purchase> purchaseList = purchaseMapper.selectPurchasePage(page, purchase);
        //将查询到的总行数和当前页数据组装到Page对象

        page.setPageCount(purchaseCount);
        page.setResultList(purchaseList);
        return page;
    }

    @Override
    public Result deletePurchase(Integer buyId) {

        int i = purchaseMapper.deletePurchaseById(buyId);

        if (i>0)
        {
         return Result.ok("删除成功");
        }

        return Result.err(Result.CODE_ERR_BUSINESS,"删除失败");
    }

    @Override
    public Result updatePurchase(Purchase purchase) {

        int i = purchaseMapper.updatePurchaseById(purchase);
        if (i>0)
        {
            return Result.ok("修改成功");
        }

        return Result.err(Result.CODE_ERR_BUSINESS,"修改失败");
    }
}

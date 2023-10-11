package com.yl.service;

import com.yl.entity.Purchase;
import com.yl.entity.Result;
import com.yl.page.Page;

public interface PurchaseService {

    //添加采购单的方法
    Result savePurchase(Purchase purchase);


    //分页查询采购单的业务方法
    public Page queryPurchasePage(Page page, Purchase purchase);



    Result deletePurchase(Integer buyId);

    Result updatePurchase(Purchase purchase);
}

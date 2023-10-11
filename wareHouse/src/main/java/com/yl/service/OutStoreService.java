package com.yl.service;

import com.yl.entity.OutStore;
import com.yl.entity.Result;
import com.yl.page.Page;

public interface OutStoreService {

    //添加出库单
    Result addOutStore(OutStore outStore);

    Page outStorePage(Page page, OutStore outStore);


    //确认出库方法
    Result confirmOutStore(OutStore outStore);
}

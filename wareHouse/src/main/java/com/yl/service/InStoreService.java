package com.yl.service;

import com.yl.entity.InStore;
import com.yl.entity.Result;
import com.yl.page.Page;

public interface InStoreService {

    //添加入库单方法
    Result saveInStore(InStore inStore, Integer buyId);

    Page queryInStorePage(Page page, InStore inStore);

    Result confirmInStore(InStore inStore);
}

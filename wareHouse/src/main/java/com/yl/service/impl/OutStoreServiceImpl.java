package com.yl.service.impl;

import com.yl.entity.OutStore;
import com.yl.entity.Result;
import com.yl.mapper.OutStoreMapper;
import com.yl.mapper.ProductMapper;
import com.yl.page.Page;
import com.yl.service.OutStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OutStoreServiceImpl implements OutStoreService {

    @Autowired
    private OutStoreMapper outStoreMapper;

    @Autowired
    private ProductMapper productMapper;
    @Override
    public Result addOutStore(OutStore outStore) {
        int i = outStoreMapper.insertOutStore(outStore);
        if (i>0){
            return Result.ok("出库单添加成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"出库单添加失败");

    }


    @Override
    public Page outStorePage(Page page, OutStore outStore) {

        int outStoreCount = outStoreMapper.outStoreCount(outStore);
        List<OutStore> outStoreList = outStoreMapper.outStorePage(page, outStore);

        page.setTotalNum(outStoreCount);
        page.setResultList(outStoreList);
        return page;
    }


    @Transactional
    @Override
    public Result confirmOutStore(OutStore outStore) {
        //判断商品库存是否充足
        int invent = productMapper.findInventById(outStore.getProductId());
        if (invent<outStore.getOutNum()){
            return Result.err(Result.CODE_ERR_BUSINESS,"商品库存不足");
        }
        //修改出库单状态
        int setIsOutById = outStoreMapper.setIsOutById(outStore.getOutsId());

        if (setIsOutById>0){
            int i = productMapper.setInventById(outStore.getProductId(), outStore.getOutNum());
            if (i>0){
                return Result.ok("确认处理成功");
            }
            return Result.err(Result.CODE_ERR_BUSINESS,"确认出库失败");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"确认出库失败");
    }
}

package com.yl.service.impl;

import com.yl.entity.Supply;
import com.yl.mapper.SupplyMapper;
import com.yl.service.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplyServiceImpl implements SupplyService {

    @Autowired
    private SupplyMapper supplyMapper;

    @Override
    public List<Supply> queryAllSupply() {
        return supplyMapper.findAllSupply();
    }
}


package com.yl.service.impl;

import com.yl.entity.Unit;
import com.yl.mapper.UnitMapper;
import com.yl.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    private UnitMapper unitMapper;
    @Override
    public List<Unit> queryAllUnit() {
        return unitMapper.findAllUnit();
    }
}

package com.yl.mapper;

import com.yl.entity.Unit;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitMapper {
    //查询所有单位的方法
    public List<Unit> findAllUnit();
}

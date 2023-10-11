package com.yl.mapper;

import com.yl.entity.Supply;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplyMapper {

    //查询所有供应商的方法
    List<Supply> findAllSupply();
}

package com.yl.mapper;

import com.yl.entity.Brand;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface BrandMapper {

    //查询所有品牌的方法
    public List<Brand> findAllBrand();
}

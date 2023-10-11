package com.yl.service.impl;


import com.yl.entity.Brand;
import com.yl.mapper.BrandMapper;
import com.yl.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<Brand> queryAllBrand() {
        return brandMapper.findAllBrand();
    }
}

package com.yl.service.impl;


import com.yl.entity.Place;
import com.yl.mapper.PlaceMapper;
import com.yl.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private PlaceMapper placeMapper;
    @Override
    public List<Place> queryAllPlace() {
        return placeMapper.findAllPlace();
    }
}

package com.yl.mapper;

import com.yl.entity.Place;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceMapper {

    //查询所有产地
    public List<Place> findAllPlace();
}

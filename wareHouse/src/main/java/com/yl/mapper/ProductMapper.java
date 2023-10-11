package com.yl.mapper;

import com.yl.entity.Product;
import com.yl.page.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper {

    //查询商品行数的方法
    Integer findProductRowCount(Product product);

    //分页查询商品的方法
    List<Product> fondProductPae(@Param("page") Page page, @Param("product") Product product);

    //添加商品的方法
    public int insertProduct(Product product);

    //根据商品id修改商品的上下架状态
    int updateStateById(Product product);


    //根据商品id删除商品的方法
    int deleteProductById(Integer productId);

    //根据商品id修改商品的方法
    int modifiedProductByPId(Product product);

    //根据商品id修改商品库存方法
    int setInventById(Integer productId,Integer invent);

    //根据商品id查询商品库存
    int findInventById(Integer productId);

}

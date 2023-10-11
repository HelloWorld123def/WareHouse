package com.yl.service;

import com.yl.entity.Product;
import com.yl.entity.Result;
import com.yl.page.Page;

public interface ProductService {

    //分页查询商品业务的方法
    Page queryProductPage(Page page, Product product);


    //添加商品的业务方法
    Result saveProduct(Product product);

    //修改商品上下架状态
    Result updateProductState(Product product);

    Result deleteProduct(Integer productId);

    //修改商品的业务方法
    Result updateProduct(Product product);
}

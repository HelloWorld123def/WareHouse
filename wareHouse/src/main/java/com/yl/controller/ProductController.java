package com.yl.controller;

import com.yl.entity.CurrentUser;
import com.yl.entity.Product;
import com.yl.entity.Result;
import com.yl.page.Page;
import com.yl.service.*;
import com.yl.utils.TokenUtils;
import com.yl.utils.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ResourceBundle;

@RequestMapping("product")
@RestController
public class ProductController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private StoreService storeService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductTypeService productTypeService;

    @Autowired
    private SupplyService supplyService;

    @Autowired
    private PlaceService placeService;

    @Autowired
    private UnitService unitService;

    /**
     * 查询所有仓库的url接口/product/store-list
     * <p>
     * 返回值Result对象给客户端响应查询到的List<Store>;
     */
    @RequestMapping("/store-list")
    public Result storeList() {

        return Result.ok("查询成功", storeService.queryAllStore());
    }

    /**
     * 查询所有品牌的url接口/product/brand-list
     * <p>
     * 返回值Result对象给客户端响应查询到的List<Brand>;
     */
    @RequestMapping("/brand-list")
    public Result brandList() {
        return Result.ok(brandService.queryAllBrand());
    }


    /**
     * 查询所有商品分类树的url接口/product/category-tree
     * <p>
     * 返回值Result对象给客户端响应查询到的所有商品分类树List<ProductType>;
     */
    @RequestMapping("/category-tree")
    public Result categoryTree() {
        return Result.ok(productTypeService.allProductTypeTree());
    }


    /**
     * 分页查询商品的url接口/product/product-page-list
     * <p>
     * 参数Page对象用于接收请求参数页码pageNum、每页行数pageSize;
     * 参数Product对象用于接收请求参数仓库id storeId、商品名称productName、
     * 品牌名称brandName、分类名称typeName、供应商名称supplyName、产地名称
     * placeName、上下架状态upDownState、是否过期isOverDate;
     * <p>
     * 返回值Result对象向客户端响应组装了所有分页信息的Page对象;
     */
    @RequestMapping("/product-page-list")
    public Result productPageList(Page page, Product product) {
        return Result.ok(productService.queryProductPage(page, product));
    }

    /**
     * 查询所有供应商的url接口/product/supply-list
     * <p>
     * 返回值Result对象给客户端响应查询到的List<Supply>;
     */
    @RequestMapping("/supply-list")
    public Result supplyList() {
        return Result.ok(supplyService.queryAllSupply());
    }

    /**
     * 查询所有产地的url接口/product/place-list
     * <p>
     * 返回值Result对象给客户端响应查询到的List<Place>;
     */
    @RequestMapping("/place-list")
    public Result placeList() {
        return Result.ok(placeService.queryAllPlace());
    }

    /**
     * 查询所有单位的url接口/product/unit-list
     * <p>
     * 返回值Result对象给客户端响应查询到的List<Unit>;
     */
    @RequestMapping("/unit-list")
    public Result unitList() {
        return Result.ok(unitService.queryAllUnit());
    }


    /**
     * 将配置文件的file.upload-path属性值注入给控制器的uploadPath属性,
     * 其为图片上传到项目的目录路径(类路径classes即resources下的static/img/upload);
     */
    @Value("${file.upload-path}")
    private String uploadPath;


    /**
     * 上传图片的url接口/product/img-upload
     * <p>
     * 参数MultipartFile file对象封装了上传的图片;
     *
     * @CrossOrigin表示该url接口允许跨域请求;
     */
    @CrossOrigin
    @PostMapping("/img-upload")
    public Result uploadImg(MultipartFile file) {
        try {
            //拿到图片上传到的目录(类路径classes下的static/img/upload)的File对象
            File uploadImgFile = ResourceUtils.getFile(uploadPath);
            // 拿到图片保存到的磁盘路径
            File uploadImgPath = uploadImgFile.getAbsoluteFile();
            //保存图片
            String fileUploadPath = uploadImgPath + "\\" + file.getOriginalFilename();
            //成功响应
            file.transferTo(new File(fileUploadPath));
            return Result.ok("保存图片成功");
        } catch (Exception e) {
            //失败响应
            return Result.err(Result.CODE_ERR_BUSINESS, "图片上传失败！");
        }
    }

    /**
     * 添加商品的url接口/product/product-add
     *
     * @RequestBody Product product将添加的商品信息的json串数据封装到参数Product对象;
     * @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token
     * 将请求头Token的值即客户端归还的token赋值给参数变量token;
     */
    @RequestMapping("/product-add")
    public Result addProduct(@RequestBody Product product,
                             @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
        //获取当前登录的用户
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);

        //获取当前登录的用户id,即添加商品的用户id
        int userId = currentUser.getUserId();

        product.setCreateBy(userId);

        return  Result.ok(  productService.saveProduct(product));
    }

    /**
     * 修改商品上下架状态的url接口/product/state-change
     *
     * @RequestBody Product product用于接收并封装请求json数据;
     */
    @RequestMapping("/state-change")
    public Result changeProductState(@RequestBody Product product){
        return Result.ok(productService.updateProductState(product));
    }


    /**
     * 删除商品的url接口/product/product-delete/{productId}
     */
    @RequestMapping("/product-delete/{productId}")
    public Result deleteProduct(@PathVariable Integer productId){
        //执行业务
        Result result = productService.deleteProduct(productId);
        //响应
        return result;
    }

    /**
     * 修改商品的url接口/product/product-update
     *
     * @RequestBody Product product将请求传递的json数据封装到参数Product对象;
     * @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token
     * 将请求头Token的值即客户端归还的token赋值给参数变量token;
     */
    @RequestMapping("/product-update")
    public Result updateProduct(@RequestBody Product product,
                                @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
        //获取当前登录的用户
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        //获取当前登录的用户id,即修改商品的用户id
        int updateBy = currentUser.getUserId();
        product.setUpdateBy(updateBy);
        return Result.ok("修改商品成功",productService.updateProduct(product));

    }
}

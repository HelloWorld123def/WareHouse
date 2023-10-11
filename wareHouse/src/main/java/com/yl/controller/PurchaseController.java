package com.yl.controller;

import com.yl.entity.*;
import com.yl.page.Page;
import com.yl.service.InStoreService;
import com.yl.service.ProductService;
import com.yl.service.PurchaseService;
import com.yl.service.StoreService;
import com.yl.utils.TokenUtils;
import com.yl.utils.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private ProductService productService;


    @Autowired
    private StoreService storeService;

    @Autowired
    private InStoreService inStoreService;

    /**
     * 添加采购单的url接口/purchase/purchase-add
     */
    @RequestMapping("/purchase-add")
    public Result addPurchase(@RequestBody Purchase purchase) {
        return Result.ok(purchaseService.savePurchase(purchase));
    }


    /**
     * 查询所有仓库的url接口/purchase/store-list
     */
    @RequestMapping("/store-list")
    public Result storeList() {
        //执行业务
        List<Store> storeList = storeService.queryAllStore();
        //响应
        return Result.ok(storeList);
    }

    /**
     * 分页查询采购单的url接口/purchase/purchase-page-list
     * <p>
     * 参数Page对象用于接收请求参数页码pageNum、每页行数pageSize;
     * 参数Purchase对象用于接收请求参数仓库id storeId、商品名称productName、
     * 采购人buyUser、是否生成入库单isIn、起止时间startTime和endTime;
     * <p>
     * 返回值Result对象向客户端响应组装了所有分页信息的Page对象;
     */
    @RequestMapping("/purchase-page-list")
    public Result purchasePageList(Page page, Purchase purchase) {
        return Result.ok(purchaseService.queryPurchasePage(page, purchase));

    }

    /**
     * 删除采购单的url接口/purchase/purchase-delete/{buyId}
     *
     * @PathVariable Integer buyId将路径占位符buyId的值赋值给参数变量buyId;
     */
    @RequestMapping("/purchase-delete/{buyId}")
    public Result deletePurchase(@PathVariable Integer buyId) {
        //执行业务
        Result result = purchaseService.deletePurchase(buyId);
        //响应
        return result;
    }

    /**
     * 修改采购单的url接口/purchase/purchase-update
     *
     * @RequestBody Purchase purchase将请求传递的json数据封装到参数Purchase对象;
     */
    @RequestMapping("/purchase-update")
    public Result updatePurchase(@RequestBody Purchase purchase) {
        //执行业务
        Result result = purchaseService.updatePurchase(purchase);
        //响应
        return result;
    }


    /**
     * 添加入库单的url接口/purchase/in-warehouse-record-add
     *
     * @RequestBody Purchase purchase将请求传递的json数据封装到参数Purchase对象;
     * @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token
     * 将请求头Token的值即客户端归还的token赋值给参数变量token;
     */
    @RequestMapping("/in-warehouse-record-add")
    public Result addInStore(@RequestBody Purchase purchase,
                             @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        //获取当前登录的用户
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        //获取当前登录的用户id -- 创建入库单的用户id
        int createBy = currentUser.getUserId();

        //创建InStore对象封装添加的入库单的信息
        InStore inStore = new InStore();

        inStore.setCreateBy(createBy);
        inStore.setStoreId(purchase.getStoreId());
        inStore.setProductId(purchase.getProductId());
        inStore.setInNum(purchase.getBuyNum());

        return Result.ok(inStoreService.saveInStore(inStore, purchase.getBuyId()));
    }


}
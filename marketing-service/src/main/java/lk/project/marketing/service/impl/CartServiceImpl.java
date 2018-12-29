package lk.project.marketing.service.impl;

import lk.project.marketing.base.bo.*;
import lk.project.marketing.base.client.RedisClient;
import lk.project.marketing.repository.CouponReceiveDetailRepository;
import lk.project.marketing.service.CartService;
import lk.project.marketing.service.CouponConsumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车Service实现
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    RedisClient redisClient;

    @Autowired
    CouponConsumeService couponConsumeService;

    @Autowired
    CouponReceiveDetailRepository couponReceiveDetailRepository;

    /**
     * 添加商品到购物车列表
     * @param productBo
     * @param memberBo
     * @return
     */
    @Override
    public Boolean addGoodsToCartList(ProductBo productBo, MemberBo memberBo) {
        //从redis中取出该用户购物车列表
        List<CartBo> cartBoList = getCartListFromRedis(memberBo.getUserId());
        if(!CollectionUtils.isEmpty(cartBoList)){
            //在购物车列表中搜索该商家购物车
            CartBo cartBo = searchCartBoByShopId(cartBoList, productBo.getShopId());
            //存在该商家购物车
            if(cartBo!=null){
                //在购物车中搜索指定商品
                ProductBo productBoFromCart = searchProductBoBySkuId(cartBo, productBo.getSkuId());
                //已存在该商品
                if(productBoFromCart!=null){
                    productBoFromCart.setQuantity(productBoFromCart.getQuantity() + productBo.getQuantity());
                }else {//不存在该商品的订单项
                    cartBo.getProductBoList().add(productBo);
                }

            }else {//不存在该商家购物车
                cartBo = createCartBo(productBo);
                cartBoList.add(cartBo);
            }
        }else {//购物车列表为空
            CartBo cartBo = createCartBo(productBo);
            cartBoList.add(cartBo);
        }
        saveCartListToRedis(memberBo.getUserId(),cartBoList);
        return true;
    }

    /**
     * 将本地购物车合并到用户购物车当中
     * @param localCartBoList
     * @param memberBo
     * @return
     */
    @Override
    public Boolean mergeCartList(List<CartBo> localCartBoList, MemberBo memberBo) {
        if (!CollectionUtils.isEmpty(localCartBoList)) {
            for(CartBo cartBo : localCartBoList){
                if (!CollectionUtils.isEmpty(cartBo.getProductBoList())) {
                    for(ProductBo productBo : cartBo.getProductBoList()){
                        addGoodsToCartList(productBo,memberBo);
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 获得指定用户的购物车列表
     * @param userId
     * @return
     */
    @Override
    public List<CartBo> getCartListFromRedis(String userId) {
        List<CartBo> cartList = redisClient.hget("cartList",userId);
        if(CollectionUtils.isEmpty(cartList)){
            cartList = new ArrayList<>();
        }
        return cartList;
    }

    /**
     * 获取指定用户的可用优惠券详情列表
     * @param memberBo
     * @return
     */
    @Override
    public List<OrderItemAvailableCouponsRespBo> getAvailableCouponDetailList(MemberBo memberBo){
        List<OrderItemAvailableCouponsRespBo> orderItemAvailableCoupons;
        orderItemAvailableCoupons = redisClient.hget(
                "orderItemAvailableCoupons", memberBo.getUserId());
        if(CollectionUtils.isEmpty(orderItemAvailableCoupons)){
            orderItemAvailableCoupons = new ArrayList<>();
        }
        return orderItemAvailableCoupons;
    }

    /**
     * 将购物车列表存入redis中
     * @param userId
     * @param cartBoList
     */
    private void saveCartListToRedis(String userId, List<CartBo> cartBoList) {
        redisClient.hset("cartList", userId, cartBoList);
    }

    /**
     * 搜索指定商家的购物车
     * @param cartBoList
     * @param shopId
     * @return
     */
    private CartBo searchCartBoByShopId(List<CartBo> cartBoList,String shopId){
        for(CartBo cartBo:cartBoList){
            if(cartBo.getShopId().equals(shopId)){
                return cartBo;
            }
        }
        return null;
    }

    /**
     * 搜索指定商品的订单项
     * @param cartBo
     * @param skuId
     * @return
     */
    private ProductBo searchProductBoBySkuId(CartBo cartBo, String skuId){
        for(ProductBo productBo : cartBo.getProductBoList()){
            if(skuId.equals(productBo.getSkuId())){
                return productBo;
            }
        }
        return null;
    }

    /**
     * 生成购物车
     * @param productBo
     * @return
     */
    private CartBo createCartBo(ProductBo productBo){
        List<ProductBo> productBoList = new ArrayList<>();
        productBoList.add(productBo);
        CartBo cartBo = new CartBo();
        cartBo.setShopId(productBo.getShopId());
        cartBo.setShopName(productBo.getShopName());
        cartBo.setProductBoList(productBoList);
        return cartBo;
    }

}

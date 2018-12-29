package lk.project.marketing.service;

import lk.project.marketing.base.bo.*;

import java.util.List;

public interface CartService {

    /**
     * 添加商品到购物车列表
     * @param productBo
     * @param memberBo
     * @return
     */
    Boolean addGoodsToCartList(ProductBo productBo, MemberBo memberBo);

    /**
     * 获得指定用户的购物车列表
     * @param userId
     * @return
     */
    List<CartBo> getCartListFromRedis(String userId);

    /**
     * 将本地购物车合并到用户购物车当中
     * @param cartBoList
     * @param memberBo
     * @return
     */
    Boolean mergeCartList(List<CartBo> cartBoList, MemberBo memberBo);

    /**
     * 获得用户可用优惠券列表
     * @param memberBo
     * @return
     */
    List<OrderItemAvailableCouponsRespBo> getAvailableCouponDetailList(MemberBo memberBo);
}

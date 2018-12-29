package lk.project.marketing.client.rpc;


import lk.project.marketing.client.dto.*;
import lk.project.marketing.client.vo.ResponseVO;

import java.util.List;

public interface CartInterface {

    /**
     * 添加商品到购物车列表
     * @param productDto
     * @param memberDto
     * @return
     */
    ResponseVO addGoodsToCartList(ProductDto productDto, MemberDto memberDto);

    /**
     * 获得指定用户的购物车列表
     * @param userId
     * @return
     */
    ResponseVO getCartListFromRedis(String userId);

    /**
     * 将本地购物车合并到用户购物车当中
     * @param cartDtoList
     * @param memberDto
     * @return
     */
    ResponseVO mergeCartList(List<CartDto> cartDtoList, MemberDto memberDto);

    /**
     * 获得用户可用优惠券列表
     * @param memberDto
     * @return
     */
    ResponseVO getAvailableCouponDetailList(MemberDto memberDto);
}

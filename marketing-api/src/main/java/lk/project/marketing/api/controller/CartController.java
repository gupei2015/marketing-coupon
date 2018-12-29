package lk.project.marketing.api.controller;

import lk.project.marketing.api.common.BaseController;
import lk.project.marketing.client.dto.CartDto;
import lk.project.marketing.client.dto.MemberDto;
import lk.project.marketing.client.dto.ProductDto;
import lk.project.marketing.client.rpc.CartInterface;
import lk.project.marketing.client.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/cart")
public class CartController extends BaseController {

    @Autowired
    private CartInterface cartInterface;

    /**
     * 添加商品到购物车列表
     * @param productDto
     * @param memberDto
     * @return
     */
    @RequestMapping("/addGoodsToCartList")
    public ResponseVO addGoodsToCartList(@RequestBody ProductDto productDto, @RequestBody MemberDto memberDto){
        return cartInterface.addGoodsToCartList(productDto, memberDto);
    }

    /**
     * 获取指定用户的购物车列表
     * @param localCartList
     * @param memberDto
     * @return
     */
    @RequestMapping("/getCartDtoList")
    public ResponseVO getCartDtoList(@RequestBody List<CartDto> localCartList, @RequestBody MemberDto memberDto){
        if(!CollectionUtils.isEmpty(localCartList)){
            //本地购物车与redis中的购物车合并
            cartInterface.mergeCartList(localCartList,memberDto);
        }
        return cartInterface.getCartListFromRedis(memberDto.getUserId());
    }

    /**
     * 获得用户可用优惠券列表
     * @param memberDto
     * @return
     */
    @RequestMapping("/getAvailableCouponDetailList")
    public ResponseVO getAvailableCouponDetailList(@RequestBody MemberDto memberDto){
        return cartInterface.getAvailableCouponDetailList(memberDto);
    }
}


package lk.project.marketing.service.rpc;

import lk.project.marketing.base.bo.CartBo;
import lk.project.marketing.base.bo.MemberBo;
import lk.project.marketing.base.bo.ProductBo;
import lk.project.marketing.base.client.RedisClient;
import lk.project.marketing.client.dto.CartDto;
import lk.project.marketing.client.dto.MemberDto;
import lk.project.marketing.client.dto.ProductDto;
import lk.project.marketing.client.rpc.CartInterface;
import lk.project.marketing.client.vo.ResponseVO;
import lk.project.marketing.service.rpc.pojo.BaseResponse;
import lk.project.marketing.repository.CouponReceiveDetailRepository;
import lk.project.marketing.service.CartService;
import lk.project.marketing.service.CouponConsumeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class CartInterfaceImpl extends BaseResponse implements CartInterface {

    @Autowired
    RedisClient redisClient;

    @Autowired
    CartService cartService;

    @Autowired
    CouponConsumeService couponConsumeService;

    @Autowired
    CouponReceiveDetailRepository couponReceiveDetailRepository;

    /**
     * 添加商品到购物车列表
     * @param productDto
     * @param memberDto
     * @return
     */
    @Override
    public ResponseVO addGoodsToCartList(ProductDto productDto, MemberDto memberDto) {
        MemberBo memberBo = new MemberBo();
        BeanUtils.copyProperties(memberDto,memberBo);
        ProductBo productBo = new ProductBo();
        BeanUtils.copyProperties(productDto,productBo);
        cartService.addGoodsToCartList(productBo,memberBo);
        return getSuccess();
    }

    /**
     * 将本地购物车合并到用户购物车当中
     * @param localCartDtoList
     * @return
     */
    @Override
    public ResponseVO mergeCartList(List<CartDto> localCartDtoList, MemberDto memberDto) {
        MemberBo memberBo = new MemberBo();
        BeanUtils.copyProperties(memberDto,memberBo);
        List<CartBo> localCartBoList = new ArrayList<>();
        CartBo cartBo = new CartBo();
        for(CartDto cartDto : localCartDtoList){
            BeanUtils.copyProperties(cartDto,cartBo);
            localCartBoList.add(cartBo);
        }
        cartService.mergeCartList(localCartBoList,memberBo);
        return getSuccess();
    }

    /**
     * 获得指定用户的购物车列表
     * @param userId
     * @return
     */
    @Override
    public ResponseVO getCartListFromRedis(String userId) {
        return getFromData(cartService.getCartListFromRedis(userId));
    }

    /**
     * 获取指定用户的可用优惠券详情列表
     * @param memberDto
     * @return
     */
    @Override
    public ResponseVO getAvailableCouponDetailList(MemberDto memberDto){
        MemberBo memberBo = new MemberBo();
        BeanUtils.copyProperties(memberDto,memberBo);
        return getFromData(cartService.getAvailableCouponDetailList(memberBo));
    }

}

package lk.project.marketing.api.controller;

import lk.project.marketing.api.common.BaseController;
import lk.project.marketing.client.dto.*;
import lk.project.marketing.client.rpc.CouponConsumeInterface;
import lk.project.marketing.client.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/couponConsume")
public class CouponConsumeController extends BaseController{
    @Autowired
    CouponConsumeInterface couponConsumeInterface;

    /**
     * 查询订单关联的优惠券使用记录
     * @param queryOrderConsumedCouponReqDto
     * @return
     */
    @PostMapping("/order/history/query")
    public ResponseVO queryOrderConsumedCoupon(@RequestBody QueryOrderConsumedCouponReqDto queryOrderConsumedCouponReqDto){
        return couponConsumeInterface.queryOrderConsumedCoupon(queryOrderConsumedCouponReqDto);
    }

    /**
     * 查询会员购买单项商品/服务可使用的优惠券
     * @param queryItemMatchedCouponReqDto
     * @return
     */
    @PostMapping("/item/available/query")
    public ResponseVO queryMatchedCouponForPurchaseItem(@RequestBody QueryItemMatchedCouponReqDto queryItemMatchedCouponReqDto){
        return couponConsumeInterface.queryMatchedCouponForPurchaseItem(queryItemMatchedCouponReqDto);
    }

    /**
     * 查询会员/用户下单可使用的优惠券
     * @param queryOrderMatchedCouponDto
     * @return
     */
    @PostMapping("/order/available/query")
    public ResponseVO queryMatchedCouponForOrder(@RequestBody QueryOrderMatchedCouponDto queryOrderMatchedCouponDto){
        return couponConsumeInterface.queryMatchedCouponForOrder(queryOrderMatchedCouponDto);
    }

    /**
     * 将可用优惠券明细列表按优先使用策略排序
     * @param sortAvailableMatchedCouponsReqDto
     * @return
     */
    @PostMapping("/available/sort")
    public ResponseVO sortAvailableMatchedCoupons(@RequestBody SortAvailableMatchedCouponsReqDto sortAvailableMatchedCouponsReqDto){
        return couponConsumeInterface.sortAvailableMatchedCoupons(sortAvailableMatchedCouponsReqDto);
    }

    /**
     * 校验用券信息,每一种优惠券按使用策略顺序更新领券记录状态,并更新用户中心优惠券信息
     * @param consumeCouponReqDto
     * @return
     */
    @PostMapping("/consume")
    public ResponseVO consumeCoupon(@RequestBody ConsumeCouponReqDto consumeCouponReqDto)throws Exception{
        return couponConsumeInterface.consumeCoupon(consumeCouponReqDto);
    }
}

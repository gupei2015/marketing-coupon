package lk.project.marketing.client.rpc;

import lk.project.marketing.client.dto.*;
import lk.project.marketing.client.vo.ResponseVO;

public interface CouponConsumeInterface {

    /**
     * 查询订单关联的优惠券使用记录
     * @param queryOrderConsumedCouponReqDto
     * @return
     */
    ResponseVO queryOrderConsumedCoupon(QueryOrderConsumedCouponReqDto queryOrderConsumedCouponReqDto);

    /**
     * 查询会员购买单项商品/服务可使用的优惠券
     * @param queryItemMatchedCouponReqDto
     * @return
     */
    ResponseVO queryMatchedCouponForPurchaseItem(QueryItemMatchedCouponReqDto queryItemMatchedCouponReqDto);

    /**
     * 查询会员/用户下单可使用的优惠券
     * @param queryOrderMatchedCouponDto
     * @return
     */
    ResponseVO queryMatchedCouponForOrder(QueryOrderMatchedCouponDto queryOrderMatchedCouponDto);

    /**
     * 将可用优惠券明细列表按优先使用策略排序
     * @param sortAvailableMatchedCouponsReqDto
     * @return
     */
    ResponseVO sortAvailableMatchedCoupons(SortAvailableMatchedCouponsReqDto sortAvailableMatchedCouponsReqDto);

    /**
     * 校验用券信息,每一种优惠券按使用策略顺序更新领券记录状态,并更新用户中心优惠券信息
     * @param consumeCouponReqDto
     * @return
     */
    ResponseVO consumeCoupon(ConsumeCouponReqDto consumeCouponReqDto)throws Exception;
}

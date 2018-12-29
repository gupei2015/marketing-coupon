package lk.project.marketing.service;

import lk.project.marketing.base.bo.CouponReceiveBo;
import lk.project.marketing.base.bo.CouponReceiveReqBo;
import lk.project.marketing.base.bo.MemberBo;
import lk.project.marketing.base.bo.OrderBo;
import lk.project.marketing.base.entity.Coupon;

import java.util.List;

/**
 * Created by gupei on 2018/9/18.
 * 优惠券发放接口
 */
public interface CouponReceiveService {

    /**
     * 单次领券接口
     * @param couponReceiveReqBo 领券请求对象
     * @param memberReqBo   会员信息
     * @returns
     */
    CouponReceiveBo produceCoupon(CouponReceiveReqBo couponReceiveReqBo,MemberBo memberReqBo);

    /**
     * 根据订单生成可发放的优惠券
     * @param qualifiedCoupons 有资格获得的优惠券
     * @param memberReqBo   会员信息
     * @param orderReqBo 订单信息
     * @returns
     */
    List<CouponReceiveBo> produceCouponForOrder(List<Coupon> qualifiedCoupons,
                                                    MemberBo memberReqBo,
                                                    OrderBo orderReqBo);

    /**
     * 优惠券退回接口
     * 判断优惠券是否可以退回；更新用户优惠券状态及用户优惠券汇总信息
     * @param refundCoupons 待退回的优惠券
     * @return 退回的优惠券
     */
    List<CouponReceiveBo> getRefundCoupon(List<CouponReceiveBo> refundCoupons);
}

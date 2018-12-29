package lk.project.marketing.service;

import lk.project.marketing.base.bo.*;
import lk.project.marketing.base.entity.Coupon;

import java.util.List;

/**
 * Created by gupei on 2018/9/18.
 * 优惠券会员中心接口, 我的优惠券、积分
 */
public interface CouponMemberCenterService {

    /**
     * 查询会员可以获得的优惠券
     * @param memberReqBo 会员信息
     * @return
     */
    List<Coupon> queryMemberQualifiedCoupon(MemberBo memberReqBo);

    /**
     * 查询用户优惠券领取记录
     * @param userId 用户Id
     * @returns 实际发放的优惠券记录
     */
    List<CouponReceiveBo> queryReceivedCoupon( long userId);

    /**
     * 查询用户优惠券使用记录
     * @param userId 用户Id
     * @return 已使用的优惠券
     */
    List<CouponConsumeBo> queryConsumedCoupon(long userId);

    /**
     * 查询用户可用优惠券
     * @param userId 用户Id
     * @return 用户可用优惠券信息
     */
    List<CouponSummaryBo> queryAvailableCoupon(long userId);

}

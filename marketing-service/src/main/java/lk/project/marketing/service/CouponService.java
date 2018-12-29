package lk.project.marketing.service;

import lk.project.marketing.base.bo.CouponReqBo;
import lk.project.marketing.base.bo.QueryCouponReqBo;
import lk.project.marketing.base.bo.UseCouponBo;
import lk.project.marketing.base.entity.Coupon;

import java.util.List;

/**
 * Created by gupei on 2018/9/12.
 */
public interface CouponService {

    /**
     * 创建优惠券及相关规则
     * @param couponRequest
     */
    Boolean createCoupon(CouponReqBo couponRequest);

    /**
     * 更新优惠券及相关规则
     * @param couponRequest
     */
    Boolean updateCoupon(CouponReqBo couponRequest);

    /**
     * 查询满足条件的优惠券
     * @param queryCouponReq
     * @return
     */
    List<Coupon> queryCoupon(QueryCouponReqBo queryCouponReq);

    /**
     * 按扣减类型排序用户请求劵列表
     * 依次扣减满减金额、折扣、积分
     * @param originUserRequestCouponList 原始用户请求劵列表
     * @return 已排序的用户请求劵列表
     */
    List<UseCouponBo> sortUserRequestCouponsByRewardType(List<UseCouponBo> originUserRequestCouponList);
}

package lk.project.marketing.backend.service;

import lk.project.marketing.base.bo.CouponReqBo;
import lk.project.marketing.base.bo.PagerBaseRespBo;
import lk.project.marketing.base.bo.QueryCouponReqBo;
import lk.project.marketing.base.entity.Coupon;

/**
 * 优惠券模板基础信息后台管理服务
 * Created by luchao on 2018/12/25.
 */
public interface CouponManagementService {

    /**
     * 创建或更新优惠券模板
     * @param couponRequest
     */
    Boolean saveCoupon(CouponReqBo couponRequest);

    /**
     * 查询满足条件的优惠券模板
     * @param queryCouponReqBo
     * @return
     */
    PagerBaseRespBo<Coupon> queryCoupon(QueryCouponReqBo queryCouponReqBo);

    /**
     * 删除优惠券模板
     * @param couponId
     * @return
     */
    Boolean deleteCoupon(Long couponId);

}

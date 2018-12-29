package lk.project.marketing.client.rpc;

import lk.project.marketing.client.vo.ResponseVO;


public interface CouponSummaryInterface {

    /**
     * 获取用户优惠券列表
     * @param userId    用户ID
     * @return
     */
    ResponseVO getUserCouponSummary(String userId);

    /**
     * 获取用户优惠券领取详情
     * @param userId    用户ID
     * @param couponId  优惠券ID
     * @return
     */
    ResponseVO getUserCouponDetail(String userId, Long couponId );

}

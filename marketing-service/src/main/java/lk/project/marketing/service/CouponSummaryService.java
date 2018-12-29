package lk.project.marketing.service;

import lk.project.marketing.base.bo.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by gupei on 2018/12/07.
 */
public interface CouponSummaryService {

    /**
     * 获取用户优惠券列表
     * @param userId    用户ID
     * @return
     */
    List<CouponSummaryBo> getUserCouponSummary(String userId);

    /**
     * 获取用户优惠券领取详情
     * @param userId    用户ID
     * @param couponId  优惠券ID
     * @return
     */
    List<CouponSummaryDetailBo> getUserCouponDetail(String userId, Long couponId );

    /**
     * 获取用户下单时指定优惠券的可用数量
     * @param couponId  优惠券Id
     * @param orderReqBo    订单信息
     * @param orderItemReqBo   订单明细项信息
     * @param memberReqBo    用户会员信息
     * @return
     */
    Long getCouponAvailableQuantityForOrder(Long couponId,
                                            OrderBo orderReqBo,
                                            OrderItemBo orderItemReqBo,
                                            MemberBo memberReqBo);


}

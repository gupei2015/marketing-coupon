package lk.project.marketing.base.bo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by alexlu on 2018/12/11.
 */
@Data
public class CouponsReduceAmountBo {
    /**
     * 扣减优惠券ID
     */
    private Long couponId;

    /**
     * 扣减优惠券金额
     */
    private BigDecimal couponReduceAmount;

    /**
     * 扣减优惠券后支付金额
     */
    private BigDecimal paymentAmount;

    /**
     * 赠送优惠券活动Id
     */
    private Long rewardCouponActivityId;

    /**
     * 赠送商品或服务ID
     */
    private String rewardProductId;

    /**
     * 赠品描述
     */
    private String rewardDesc;
}

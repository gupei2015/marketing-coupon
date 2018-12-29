package lk.project.marketing.base.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by alexlu on 2018/10/29.
 */
@Data
public class CouponConsumeDetailBo implements Serializable {
    /**
     * 消费优惠券ID
     */
    private Long couponConsumeId;

    /**
     * 发放优惠券ID
     */
    private Long couponReceiveId;

    /**
     * 发放优惠券明细ID集合(逗号间隔
     */
    private String couponReceiveDetailIds;

    /**
     * 促销活动ID
     */
    private Long activityId;

    /**
     * 本次使用数量
     */
    private Long consumeQuantity;
}

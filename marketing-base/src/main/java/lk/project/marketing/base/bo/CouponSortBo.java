package lk.project.marketing.base.bo;

import lombok.Data;

/**
 * Created by alexlu on 2018/12/13.
 */
@Data
public class CouponSortBo {
    /**
     * 消费优惠券(模板)ID
     */
    private Long couponId;

    /**
     * 本次消费优惠券数量
     */
    private Long couponQuantity;

    /**
     * 扣减类型排序值(用于设置顺序扣减)
     */
    private Integer rewardTypeReduceSeqNo;
}

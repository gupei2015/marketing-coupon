package lk.project.marketing.client.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by alexlu on 2018/11/22.
 */
@Data
public class DynamicCalcSelectedCouponDto implements Serializable {
    /**
     * 优惠券ID
     */
    private Long couponId;

    /**
     * 用户选择的优惠券使用数量
     */
    private Long couponSelectQuantity;

    /**
     * 券额(积分数,金额,折扣率等)
     */
    private BigDecimal couponAmount;

    /**
     * 当前最新状态(0-未选中,1-已选中,2-不可使用)
     * UI传入和后台返回通用
     */
    private Integer status;

    /**
     * 优惠券扣除的金额
     */
    private BigDecimal couponReducedAmount;
}

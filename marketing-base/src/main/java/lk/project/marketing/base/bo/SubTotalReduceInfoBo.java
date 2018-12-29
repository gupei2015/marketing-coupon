package lk.project.marketing.base.bo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by alexlu on 2018/12/3.
 */
@Data
public class SubTotalReduceInfoBo {
    /**
     * 满减条件扣减金额小计
     */
    private BigDecimal subTotalFullConditionReduceAmount;

    /**
     * 折扣条件扣减金额小计
     */
    private BigDecimal subTotalDiscountReduceAmount;

    /**
     * 积分兑换扣减金额小计
     */
    private BigDecimal subTotalCreditExchangeReduceAmount;

    /**
     * (单品)一口价/优惠价条件扣减金额小计
     */
    private BigDecimal subTotalOnceReduceAmount;
}

package lk.project.marketing.base.bo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by alexlu on 2018/12/3.
 */
@Data
public class CalcCouponReduceResultBo {
    /**
     * 校验每种优惠券是否匹配结算递减的满减条件是否成功
     */
    private Boolean calcCouponReduceSucceed;

    /**
     * 参与下一个劵种计算匹配递减规则后的总金额
     */
    private BigDecimal calcResultTotalAmount;

    /**
     * 最终计算每种优惠券匹配递减后的实际总金额
     */
    private BigDecimal finalTotalResultAmount;
}

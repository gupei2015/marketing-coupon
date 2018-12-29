package lk.project.marketing.base.bo;

import lk.project.marketing.base.entity.AccountRule;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 计算订单或商品的每种优惠券本次应扣结算规则信息
 * Created by alexlu on 2018/12/3.
 */
@Data
public class CalcTotalReduceByEachCouponReqBo {
    /**
     * 结算规则信息
     */
    private AccountRule accountRuleInfo;

    /**
     * 优惠券使用数量
     */
    private Long couponQuantity;

    /**
     * 商品/服务销售原单价
     */
    private BigDecimal goodsSalePrice;

    /**
     * 临时变量(当前商品或订单的总金额)
     */
    private BigDecimal tmpTotalAmount;
}

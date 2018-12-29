package lk.project.marketing.base.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单结算结果类
 * Created by alexlu on 2018/11/20.
 */
@Data
public class OrderSettleResultBo implements Serializable {

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 订单参与计算总金额(动态选劵后参与满减条件计算的虚拟总金额)
     */
    private BigDecimal totalOrderAmount;

    /**
     * 订单原始(优惠前)总金额
     */
    private BigDecimal originOrderAmount;

    /**
     * 结算后实际应付金额
     */
    private BigDecimal payOrderAmount;

    /**
     * 订单总优惠金额
     */
    private BigDecimal totalReduceAmount;

    /**
     * 优惠劵满减扣减金额
     */
    private BigDecimal subTotalFullConditionReduceAmount;

    /**
     * 优惠劵折扣扣减金额
     */
    private BigDecimal subTotalDiscountReduceAmount;

    /**
     * 优惠劵积分兑换金额
     */
    private BigDecimal subTotalCreditExchangeReduceAmount;

    /**
     * 优惠劵一次性扣减(一口价单价商品)金额
     */
    private BigDecimal subTotalOnceReduceAmount;
}

package lk.project.marketing.base.bo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountRuleReqBo extends PagerBaseReqBo{

    /**
     * 结算规则ID
     */
    private Long id;

    /**
     * 结算规则名称
     */
    private String ruleName;

    /**
     * 规则描述
     */
    private String ruleDesc;

    /**
     * 满减条件类型 0:无条件;1:满金额;2:满数量;3:优惠价
     */
    private Integer thresholdType;

    /**
     * 满减条件阈值, 0表示无条件阈值
     */
    private BigDecimal rewardThreshold;

    /**
     * 扣减类型 0:积分;1:抵扣金额;2:折扣;3:其他赠品
     */
    private Integer rewardType;

    /**
     * 扣减数,包括扣减积分数,金额,折扣率
     */
    private BigDecimal rewardAmount;

    /**
     * 赠送商品或服务ID
     */
    private String rewardProduct;

    /**
     * 赠送优惠券ID
     */
    private Long rewardActivityId;

    /**
     * 赠品描述
     */
    private String rewardDesc;

    /**
     *优惠价
     */
    private BigDecimal promotionPrice;

    /**
     * 金额兑换积分比例,积分除以该比例等于金额
     */
    private BigDecimal exchangeRatio;
}

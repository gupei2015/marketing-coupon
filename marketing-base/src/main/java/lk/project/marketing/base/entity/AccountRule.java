package lk.project.marketing.base.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by zhanghongda on 2018/10/26.
 * 优惠券结算规则
 */
@TableName("t_coupon_account_rule")
@Data
public class AccountRule extends BaseEntity<AccountRule> {

    /**
     * 结算规则名称
     */
    @TableField("rule_name")
    private String ruleName;

    /**
     * 规则描述
     */
    @TableField("rule_desc")
    private String ruleDesc;

    /**
     * 满减条件类型 0:无条件;1:满金额;2:满数量;3:优惠价
     */
    @TableField("threshold_type")
    private Integer thresholdType;

    /**
     * 满减条件阈值, 0表示无条件阈值
     */
    @TableField("reward_threshold")
    private BigDecimal rewardThreshold;

    /**
     * 扣减类型 0:积分;1:抵扣金额;2:折扣;3:其他赠品
     */
    @TableField("reward_type")
    private Integer rewardType;

    /**
     * 扣减数,包括扣减积分数,金额,折扣率
     */
    @TableField("reward_amount")
    private BigDecimal rewardAmount;

    /**
     * 赠送商品或服务ID
     */
    @TableField("reward_product")
    private String rewardProduct;

    /**
     * 赠送优惠券ID
     */
    @TableField("reward_activity_id")
    private Long rewardActivityId;

    /**
     * 赠品描述
     */
    @TableField("reward_desc")
    private String rewardDesc;

    /**
     *优惠价
     */
    @TableField("promotion_price")
    private BigDecimal promotionPrice;

    /**
     * 金额兑换积分比例,积分除以该比例等于金额
     */
    @TableField("exchange_ratio")
    private BigDecimal exchangeRatio;

}

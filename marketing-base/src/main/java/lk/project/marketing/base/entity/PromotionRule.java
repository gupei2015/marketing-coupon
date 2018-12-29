package lk.project.marketing.base.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * Created by gupei on 2018/09/13.
 * 活动规则
 */
@TableName("t_promotion_rule")
@Data
public class PromotionRule extends BaseEntity<PromotionRule> {

    /**
     * 促销活动ID
     */
    @TableField("activity_id")
    private Long activityId;

    /**
     * 规则名称
     */
    @TableField("rule_name")
    private String ruleName;

    /**
     * 规则类型 0:发放规则;1:使用规则
     */
    @TableField("rule_type")
    private Integer ruleType;

    /**
     * 用户适用条件 json字符串
     */
    @TableField("user_condition")
    private String userCondition;

    /**
     * 商品或服务适用条件 json字符串
     */
    @TableField("sku_condition")
    private String skuCondition;

    @TableField("order_condition")
    private String orderCondition;

    /**
     * 适用时间范围条件 条件表达式
     */
    @TableField("time_condition")
    private String timeCondition;

    /**
     * 其他使用范围条件 json字符串
     */
    @TableField("extras_condition")
    private String extrasCondition;

}

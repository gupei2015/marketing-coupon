package lk.project.marketing.base.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by gupei on 2018/09/13.
 * 促销活动
 */
@TableName("t_promotion_activity")
@Data
public class PromotionActivity extends BaseEntity<PromotionActivity> {

    /**
     * 基础优惠券id
     */
    @TableField("coupon_id")
    private Long couponId;

    /**
     * 促销活动名称
     */
    @TableField("activity_name")
    private String activityName;

    /**
     * 促销活动描述
     */
    @TableField("activity_desc")
    private String activityDesc;

    /**
     * 优惠券活动开始日期
     */
    @TableField("activity_start")
    private Date activityStart;

    /**
     * 优惠券活动结束日期
     */
    @TableField("activity_end")
    private Date activityEnd;

    /**
     * 优惠券生效日期
     */
    @TableField("effect_date")
    private Date effectDate;

    /**
     * 优惠券有效天数,用于发券开始计算有效截至期
     */
    @TableField("effect_days")
    private Integer effectDays;

    /**
     * 优惠券使用开始时间
     */
    @TableField("start_time")
    private Time startTime;

    /**
     * 优惠券使用结束时间
     */
    @TableField("end_time")
    private Time endTime;

    /**
     * 数量限制，无限制则不需要发券，自动用券
     */
    @TableField("is_quantityLimit")
    private Boolean quantityLimit;

    /**
     * 总发放数量
     */
    @TableField("issue_quantity")
    private Long issueQuantity;

    /**
     * 已发放数量
     */
    @TableField("received_quantity")
    private Long receivedQuantity;

    /**
     * 每人最多领取数量
     */
    @TableField("limit_quantity")
    private Long limitQuantity;

    /**
     * 自动发放数量
     */
    @TableField("auto_issue_quantity")
    private Integer autoIssueQuantity;

    /**
     * 自动发放时间
     */
    @TableField("auto_issue_time")
    private Timestamp autoIssueTime;

    /**
     * 状态，0表示未开始，1表示进行中，2表示结束
     */
    @TableField("status")
    private Integer status;

    /**
     * 活动对应的促销规则
     */
    private List<PromotionRule> promotionRules;

}

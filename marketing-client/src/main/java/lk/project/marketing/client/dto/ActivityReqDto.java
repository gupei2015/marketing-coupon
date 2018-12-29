package lk.project.marketing.client.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Data
public class ActivityReqDto implements Serializable {

    /**
     * 促销活动ID
     */
    private Long id;

    /**
     * 基础优惠券id
     */
    private Long couponId;

    /**
     * 促销活动名称
     */
    private String activityName;

    /**
     * 促销活动描述
     */
    private String activityDesc;

    /**
     * 优惠券活动开始日期
     */
    private Date activityStart;

    /**
     * 优惠券活动结束日期
     */
    private Date activityEnd;

    /**
     * 优惠券生效日期
     */
    private Date effectDate;

    /**
     * 优惠券有效天数,用于发券开始计算有效截至期
     */
    private Integer effectDays;

    /**
     * 优惠券使用开始时间
     */
    private Time startTime;

    /**
     * 优惠券使用结束时间
     */
    private Time endTime;

    /**
     * 数量限制，无限制则不需要发券，自动用券
     */
    private Boolean quantityLimit;

    /**
     * 总发放数量
     */
    private Long issueQuantity;

    /**
     * 已发放数量
     */
    private Long receivedQuantity;

    /**
     * 每人最多领取数量
     */
    private Long limitQuantity;

    /**
     * 自动发放数量
     */
    private Integer autoIssueQuantity;

    /**
     * 自动发放时间
     */
    private Timestamp autoIssueTime;

    /**
     * 状态，0表示未开始，1表示进行中，2表示结束
     */
    private Integer status;
}

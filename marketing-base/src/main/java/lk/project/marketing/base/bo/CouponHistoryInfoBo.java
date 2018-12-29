package lk.project.marketing.base.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

/**
 * 劵信息历史查询
 * Created by alexlu on 2018/11/1.
 */
@Data
public class CouponHistoryInfoBo implements Serializable {
    /**
     * 促销活动ID
     */
    private Long activityId;

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
     * 优惠券使用开始时间
     */
    private Time startTime;

    /**
     * 优惠券使用结束时间
     */
    private Time endTime;

    /**
     * 优惠券编号ID
     */
    private Long couponId;

//    /**
//     * 优惠券编码,发券时按规则生成
//     */
//    private String couponNo;

    /**
     * 优惠券生效日期
     */
    private Date startDate;

    /**
     * 优惠券截至日期
     */
    private Date endDate;

    /**
     *券额,包含积分数,金额,折扣率
     */
    private BigDecimal couponAmount;

    /**
     * 用券数量
     */
    private Long consumeUsedQuantity;

    /**
     * 优惠券领取场景描述
     */
    private String remark;

    /**
     * 历史领取总数
     */
    private Long receivedQuantity;

    /**
     * 历史使用总数
     */
    private Long consumedQuantity;

    /**
     * 历史回馈扣减总数
     */
    private BigDecimal rewardAmount;

    /*
     * 可用优惠券张数/积分余额
     */
    private Long couponAvailableQuantity;
}

package lk.project.marketing.base.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * 待使用优惠券明细信息类
 * Created by alexlu on 2018/11/13.
 */
@Data
public class AvailableCouponDetailsBo implements Serializable {
    /**
     * 消费优惠券(模板)记录ID
     */
    private Long couponId;

    /**
     * 优惠券领劵主记录ID
     */
    private Long couponReceiveId;

    /**
     * 优惠券领劵剩余数量
     */
    private Long couponReceiveRemainQuantity;

    /**
     * 优惠券领劵记录状态(0-已领取未使用,1-已使用,2-使用中,3-已过期)
     */
    private Integer couponReceiveStatus;

    /**
     * 消费优惠券领劵明细记录ID
     */
    private Long couponReceiveDetailId;

    /**
     * 优惠券领劵明细记录状态(0-已领取未使用,1-已使用,2-使用中,3-已过期)
     */
    private Integer couponReceiveDetailStatus;

    /**
     * 优惠券编码,发券时按规则生成
     */
    private Long couponNo;

    /**
     * 券额(积分数,金额,折扣率等)
     */
    private BigDecimal couponAmount;

    /**
     * 优惠券二维码地址URL
     */
    private String couponQrCodeUrl;

    /**
     * 权重值,数值小表示优先
     */
    private Integer couponWeight;

    /**
     * 优惠券使用截至日期
     */
    private Date couponEndDate;
}

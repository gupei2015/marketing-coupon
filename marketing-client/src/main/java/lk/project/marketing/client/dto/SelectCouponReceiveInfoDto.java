package lk.project.marketing.client.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Data
public class SelectCouponReceiveInfoDto implements Serializable {

    /**
     * 优惠券领劵主记录ID
     */
    private Long couponReceiveId;

    /**
     * 消费优惠券(模板)记录ID
     */
    private Long couponId;

    /**
     * 优惠券领劵剩余数量
     */
    private Long couponReceiveRemainQuantity;

    /**
     * 券额,包含积分数,金额,折扣率
     */
    private BigDecimal couponAmount;

    /**
     * 优惠券领劵记录状态(0-已领取未使用,1-已使用,2-使用中,3-已过期)
     */
    private Integer couponReceiveStatus;

    /**
     * 权重值,数值小表示优先
     */
    private Integer couponWeight;

    /**
     * 优惠券使用截至日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date couponEndDate;
}

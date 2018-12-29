package lk.project.marketing.client.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Data
public class AvailableCouponDetailsDto implements Serializable {

    /**
     * 消费优惠券(模板)记录ID
     */
    private Long couponId;

    /**
     * 消费优惠券领劵明细记录ID
     */
    private Long couponDetailId;

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

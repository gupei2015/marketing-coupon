package lk.project.marketing.base.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by alexlu on 2018/11/28.
 */
@Data
public class CouponTemplateInfoBo implements Serializable {
    /**
     * 消费优惠券(模板)记录ID
     */
    private Long couponId;

    /**
     * 消费优惠券(模板)名称
     */
    private String couponName;

    /**
     * 消费优惠券(模板)描述
     */
    private String couponDesc;

    /**
     * 消费优惠券(模板)图标URL
     */
    private String couponIcon;

    /**
     * 券额(积分数,金额,折扣率等)
     */
    private BigDecimal couponAmount;

    /**
     * 权重值,数值小表示优先
     */
    private Integer couponWeight;
}

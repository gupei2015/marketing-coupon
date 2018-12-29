package lk.project.marketing.base.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by gupei on 2018/9/18.
 * 优惠券使用对象
 */
@Data
public class CouponConsumeBo implements Serializable {

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 原订单金额
     */
    private BigDecimal originalAmount;

    /**
     * 优惠后订单实际支付金额
     */
    private BigDecimal finalPayAmount;


    /**
     * 使用优惠券明细记录列表
     */
    private List<CouponConsumeDetailBo> couponConsumeDetailList;
}

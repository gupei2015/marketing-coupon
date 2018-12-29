package lk.project.marketing.base.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 用劵历史信息
 * Created by alexlu on 2018/11/2.
 */
@Data
public class CouponConsumeHistoryRespBo implements Serializable {
//    /**
//    * 租户ID
//    */
//    private Long companyId;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 卖家商户供应商ID
     */
    private String shopId;

    /**
     * 原订单或商品项金额
     */
    private BigDecimal originalAmount;

    /**
     * 优惠后订单或商品项实际支付金额
     */
    private BigDecimal finalPayAmount;

    /**
     * 使用优惠券模板相关明细列表
     */
    private List<CouponHistoryInfoBo> couponHistoryInfoBoList;
}

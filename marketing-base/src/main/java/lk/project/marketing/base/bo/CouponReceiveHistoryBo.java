package lk.project.marketing.base.bo;

import lk.project.marketing.base.entity.CouponReceiveDetail;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 发劵历史信息
 * Created by alexlu on 2018/11/2.
 */
@Data
public class CouponReceiveHistoryBo implements Serializable {
    /**
    * 租户ID
    */
    private Long companyId;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 原订单或商品项金额
     */
    private BigDecimal originalAmount;

    /**
     * 优惠后订单或商品项实际支付金额
     */
    private BigDecimal finalPayAmount;

    /**
     * 发领优惠券模板相关明细列表
     */
    private Map<CouponHistoryInfoBo, List<CouponReceiveDetail>> couponReceiveHistoryDataList;
}

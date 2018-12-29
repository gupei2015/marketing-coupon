package lk.project.marketing.base.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 用户使用优惠券处理操作类
 * Created by alexlu on 2018/11/7.
 */
@Data
public class UserCouponConsumeProcessBo implements Serializable {
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户编号
     */
    private String userCode;

    /**
     * 商家店铺ID
     */
    private String shopId;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 订单明细项ID
     */
    private String orderItemId;

//    /**
//     * 商品/服务ID
//     */
//    private String skuId;

    /**
     * 订单项/商品购买数量
     */
    private Long goodsQuantity;

    /**
     * 原订单或商品项金额
     */
    private BigDecimal originalAmount;

    /**
     * 优惠后订单或商品项实际支付金额
     */
    private BigDecimal finalPayAmount;

    /**
     * 各种优惠券扣减金额信息
     */
    private List<CouponsReduceAmountBo> couponReduceAmountList;
}

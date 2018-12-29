package lk.project.marketing.client.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 优惠券扣减信息DTO
 * Created by alexlu on 2018/11/22.
 */
@Data
public class CouponReduceInfoDto implements Serializable {
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 优惠券扣减目标类型(1-订单,2-商品)
     */
    private Integer couponReduceTargetFlag;

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

    /**
     * 商品/服务SKU ID
     */
    private String skuId;

    /**
     * 商品/服务销售单价
     */
    private BigDecimal goodsSalePrice;

    /**
     * 商品/服务数量
     */
    private Long goodsQuantity;

    /**
     * 商家ID
     */
    private String shopId;
}

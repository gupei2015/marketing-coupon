package lk.project.marketing.client.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderItemDto implements Serializable {

    /**
     * 订单明细项ID
     */
    private String orderItemId;

    /**
     * 商品/服务SKU ID
     */
    private String skuId;

    /**
     * 商家ID
     */
    private String shopId;

    /**
     * 商铺名称
     */
    private String shopName;

    /**
     * 商品/服务分类
     */
    private String skuCategory;

    /**
     * 商品/服务销售单价
     */
    private BigDecimal goodsSalePrice;

    /**
     * 商品/服务数量
     */
    private Long goodsQuantity;

    /**
     * 商品/服务原始金额
     */
    private BigDecimal totalGoodsAmount;

    /**
     * 结算后实际应付金额
     */
    private BigDecimal payGoodsAmount;

    /**
     * 所使用的优惠券
     */
    private List<UseCouponDto> useCoupons;
}

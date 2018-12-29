package lk.project.marketing.base.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by gupei on 2018/9/18.
 * 订单接口对象, 包含购买商品/服务明细及使用优惠券信息 .供结算使用
 */
@Data
public class OrderItemBo implements Serializable {

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
     * 商品/服务原始金额
     */
    private BigDecimal totalGoodsAmount;

    /**
     * 商品/服务数量
     */
    private Long goodsQuantity;

    /**
     * 结算后实际应付金额
     */
    private BigDecimal payGoodsAmount;

    /**
     * 所使用的优惠券
     */
    private List<UseCouponBo> useCoupons;
}

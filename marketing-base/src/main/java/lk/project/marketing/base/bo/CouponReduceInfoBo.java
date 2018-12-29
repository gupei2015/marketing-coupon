package lk.project.marketing.base.bo;

import lk.project.marketing.base.constant.CommonConstants;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 优惠券扣减信息
 * Created by alexlu on 2018/11/22.
 */
@Data
public class CouponReduceInfoBo implements Serializable {
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
     * 商家ID
     */
    private String shopId;

    @Override
    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        if (!super.equals(o)) return false;
//        CouponReduceInfoBo that = (CouponReduceInfoBo) o;
//        return Objects.equals(orderId, that.orderId) &&
//                Objects.equals(skuId, that.skuId);

        if(this.getCouponReduceTargetFlag().equals(CommonConstants.COUPON_REDUCE_TARGET_TYPE_ORDER)){
            return o instanceof CouponReduceInfoBo && (this.orderId.equals(((CouponReduceInfoBo)o).orderId));
        }else if(this.getCouponReduceTargetFlag().equals(CommonConstants.COUPON_REDUCE_TARGET_TYPE_ORDER_ITEM)){
            return o instanceof CouponReduceInfoBo && (this.orderItemId.equals(((CouponReduceInfoBo)o).orderItemId));
        }

        return true;
    }

    @Override
    public int hashCode() {
//        return Objects.hash(super.hashCode(), orderId, skuId);

        if(this.getCouponReduceTargetFlag().equals(CommonConstants.COUPON_REDUCE_TARGET_TYPE_ORDER)){
            return StringUtils.isNotBlank(this.orderId) ? Integer.parseInt(this.orderId) : 0;
        }else if(this.getCouponReduceTargetFlag().equals(CommonConstants.COUPON_REDUCE_TARGET_TYPE_ORDER_ITEM)){
            return StringUtils.isNotBlank(this.orderItemId) ? Integer.parseInt(this.orderItemId) : 0;
        }

        return 0;
    }
}

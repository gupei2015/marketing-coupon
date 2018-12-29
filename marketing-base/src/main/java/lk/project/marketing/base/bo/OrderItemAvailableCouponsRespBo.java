package lk.project.marketing.base.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 查询订单明细项(商品)可用优惠券结果类
 * Created by alexlu on 2018/11/13.
 */
@Data
public class OrderItemAvailableCouponsRespBo extends GetAvailableCouponSelectListBo implements Serializable {
    /**
     * 商品/服务SKU ID
     */
    private String skuId;

    /**
     * 商品/服务分类
     */
    private String skuCategory;
}

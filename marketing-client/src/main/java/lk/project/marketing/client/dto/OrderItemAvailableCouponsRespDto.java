package lk.project.marketing.client.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderItemAvailableCouponsRespDto extends GetAvailableCouponDetailListDto implements Serializable {

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
}

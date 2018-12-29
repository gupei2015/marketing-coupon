package lk.project.marketing.client.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CouponReceiveReqDto implements Serializable {

    /**
     * 优惠券模版ID，当不指定促销活动ID时，按该优惠券模版默认有效活动发券
     */
    private Long couponId;

    /**
     * 促销活动ID
     */
    private Long activityId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 领券数量
     */
    private Integer requestQuantity;

    /**
     * 领券场景描述
     */
    private String sceneDesc;

    private OrderDto orderDto;

    private OrderItemDto orderItemDto;
}

package lk.project.marketing.client.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UseCouponDto implements Serializable {
    /**
     * 消费优惠券(模板)ID
     */
    private Long couponId;

    /**
     * 本次消费优惠券数量
     */
    private Long couponQuantity;
}

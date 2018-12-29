package lk.project.marketing.client.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by alexlu on 2018/11/23.
 */
@Data
public class QueryOrderConsumedCouponReqDto implements Serializable {
    /**
     * 订单ID
     */
    private String orderId;
}

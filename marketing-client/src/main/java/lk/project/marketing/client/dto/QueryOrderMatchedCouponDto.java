package lk.project.marketing.client.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by alexlu on 2018/11/23.
 */
@Data
public class QueryOrderMatchedCouponDto implements Serializable {
    /**
     * 用户基本信息
     */
    private MemberDto memberDto;

    /**
     * 订单信息
     */
    private OrderDto orderDto;
}

package lk.project.marketing.client.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by alexlu on 2018/11/23.
 */
@Data
public class DynamicSelectCouponReqDto implements Serializable {
    /**
     * 动态计算用户选中的优惠券列表
     * 返回计算后的实际可用劵列表
     */
    List<DynamicCalcSelectedCouponDto> dynamicCalcSelectedCouponList;

    /**
     * 当前最新的总金额
     */
    private BigDecimal totalAmount;

    /**
     * 用户信息
     */
    private MemberDto memberDto;

    /**
     * 订单信息
     */
    private OrderDto orderDto;

    /**
     * 订单项信息
     */
    private OrderItemDto orderItemDto;
}

package lk.project.marketing.client.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;
import java.util.List;

@Data
public class OrderDto implements Serializable {
    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 订单总金额(动态选劵后参与满减条件计算的虚拟总金额)
     */
    private BigDecimal totalOrderAmount;

    /**
     * 订单原始(优惠前)总金额
     */
    private BigDecimal originOrderAmount;

    /**
     * 结算后实际应付金额
     */
    private BigDecimal payOrderAmount;

    /**
     * 下单时间
     */
    private Date orderDate;

    /**
     * 预约时间
     */
    private Time reserveTime;

    /**
     * 订单明细项
     */
    private List<OrderItemDto> orderItemList;

    /**
     * 订单所使用的优惠券
     */
    private List<UseCouponDto> useCoupons;
}

package lk.project.marketing.base.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 * Created by gupei on 2018/9/18.
 * 订单接口对象, 包含购买商品/服务明细及使用优惠券信息 .供结算使用
 */
@Data
public class OrderBo implements Serializable {
    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 订单参与计算总金额(动态选劵后参与满减条件计算的虚拟总金额)
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
    private List<OrderItemBo> orderItemList;

    /**
     * 订单所使用的优惠券
     */
    private List<UseCouponBo> useCoupons;
}

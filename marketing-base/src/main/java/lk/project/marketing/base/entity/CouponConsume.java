package lk.project.marketing.base.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by zhanghongda on 2018/10/26.
 * 用券实体对象
 */
@TableName("t_coupon_consume")
@Data
public class CouponConsume extends BaseEntity<CouponConsume> {

    /**
     * 用户买家ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 用户买家编号
     */
    @TableField("user_code")
    private String userCode;

    /**
     * 卖家商户供应商ID
     */
    @TableField("shop_id")
    private String shopId;

    /**
     * 基础优惠券id,使用积分时设置
     */
    @TableField("coupon_id")
    private Long couponId;

    /**
     * 订单ID
     */
    @TableField("order_id")
    private String orderId;

    /**
     * 订单编号
     */
    @TableField("order_no")
    private String orderNo;

    /**
     * 订单明细项ID
     */
    @TableField("order_item_id")
    private String orderItemId;

    /**
     * 原订单或商品项金额
     */
    @TableField("original_amount")
    private BigDecimal originalAmount;

    /**
     * 券额,包含积分数,金额,折扣率
     */
    @TableField("coupon_amount")
    private BigDecimal couponAmount;

    /**
     * 用券数量
     */
    @TableField("consume_quantity")
    private Long consumeQuantity;

    /**
     * 优惠后订单或商品项实际支付金额
     */
    @TableField("final_pay_amount")
    private BigDecimal finalPayAmount;

    /**
     * 用劵状态: 默认为0,支付回调成功后为1
     */
    @TableField("status")
    private Integer status;

//    /**
//     * 使用优惠券模板
//     */
//    private Coupon coupon;

//    /**
//     * 用券详情列表
//     */
//    private List<CouponConsumeDetail> couponConsumeDetailList;
}

package lk.project.marketing.base.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * Created by gupei on 2018/09/20.
 * 领券/发券实体对象
 */
@TableName("t_coupon_receive")
@Data
public class CouponReceive extends BaseEntity<CouponReceive> {

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
     * 优惠券编号ID
     */
    @TableField("coupon_id")
    private Long couponId;

    /**
     * 促销活动ID
     */
    @TableField("activity_id")
    private Long activityId;

    /**
     * 券额,包含积分数,金额,折扣率
     */
    @TableField("coupon_amount")
    private BigDecimal couponAmount;

    /**
     * 领券数量
     */
    @TableField("receive_quantity")
    private Long receiveQuantity;

    /**
     * 使用后剩余数量
     */
    @TableField("remain_quantity")
    private Long remainQuantity;

    /**
     * 优惠券生效日期
     */
    @TableField("start_date")
    private Date startDate;

    /**
     * 优惠券截至日期
     */
    @TableField("end_date")
    private Date endDate;

    /**
     * 优惠券领取场景描述
     */
    @TableField("remark")
    private String remark;

    /**
     * 领券关联订单ID
     */
    @TableField("order_id")
    protected String orderId;

    /**
     * 领券关联订单号码
     */
    @TableField("order_no")
    protected String orderNo;

    /**
     * 领券关联订单明细ID
     */
    @TableField("order_item_id")
    protected String orderItemId;

    /**
     * 状态，1为已使用，0为已领取未使用，2为已过期,
     */
    @TableField("status")
    private Integer status;

//    /**
//     * 此模板优惠券信息
//     */
//    private Coupon coupon;

//    /**
//     * 此模板优惠券对应的活动信息
//     */
//    private PromotionActivity promotionActivity;

//    /**
//     * 此模板优惠券领取的详情列表
//     */
//    private List<CouponReceiveDetail> couponReceiveDetailList;
}

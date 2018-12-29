package lk.project.marketing.base.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by gupei on 2018/09/20.
 * 我的优惠券实体对象
 */
@TableName("t_coupon_summary")
@Data
public class CouponSummary extends BaseEntity<CouponSummary> {

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
     * 券额,金额,折扣率
     */
    @TableField("coupon_amount")
    private BigDecimal couponAmount;

    /**
     * 历史领取总数
     */
    @TableField("received_quantity")
    private Long receivedQuantity;

    /**
     * 历史使用总数
     */
    @TableField("consumed_quantity")
    private Long consumedQuantity;

    /**
     * 历史回馈扣减/优惠总金额
     */
    @TableField("reward_amount")
    private BigDecimal rewardAmount;

    /**
     * 可用优惠券张数/积分数量
     */
    @TableField("coupon_quantity")
    private Long couponQuantity;


}

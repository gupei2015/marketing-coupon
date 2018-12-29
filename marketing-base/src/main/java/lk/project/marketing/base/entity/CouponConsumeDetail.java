package lk.project.marketing.base.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * Created by alexlu on 2018/10/29.
 */
@Data
@TableName("t_coupon_consume_detail")
public class CouponConsumeDetail extends BaseEntity<CouponConsumeDetail> {
    /**
     * 消费优惠券ID
     */
    @TableField("coupon_consume_id")
    private Long couponConsumeId;

    /**
     * 发放优惠券ID
     */
    @TableField("coupon_receive_id")
    private Long couponReceiveId;

    /**
     * 发放优惠券明细ID集合(逗号间隔
     */
    @TableField("coupon_receive_detail_ids")
    private String couponReceiveDetailIds;

    /**
     * 促销活动ID
     */
    @TableField("activity_id")
    private Long activityId;

    /**
     * 本次使用数量
     */
    @TableField("consume_quantity")
    private Long consumeQuantity;

//    /**
//     *发券信息
//     */
//    private CouponReceive couponReceive;
}

package lk.project.marketing.base.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by gupei on 2018/09/20.
 * 领券/发券详情实体对象
 */
@TableName("t_coupon_receive_detail")
@Data
public class CouponReceiveDetail extends BaseEntity<CouponReceiveDetail> {
    /**
     * 优惠券领取ID
     */
    @TableField("coupon_receive_id")
    private Long couponReceiveId;

    /**
     * 优惠券编码,发券时按规则生成
     */
    @TableField("coupon_no")
    private String couponNo;

    /**
     * 券额(积分数,金额,折扣率等)
     */
    @TableField("coupon_amount")
    private BigDecimal couponAmount;

    /**
     * 优惠券二维码地址URL
     */
    @TableField("coupon_qr_code_url")
    private String couponQrCodeUrl;

    /**
     * 状态，0为已领取未使用，1为已使用，2为使用中，3为已过期
     */
    @TableField("status")
    private Integer status;
}

package lk.project.marketing.base.bo;

import lk.project.marketing.base.entity.Coupon;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户每次选择优惠券操作信息
 * (对应传入的(查询可使用的优惠券接口返回的)SelectCouponUseBo对象)
 * Created by alexlu on 2018/11/22.
 */
@Data
public class DynamicCalcSelectedCouponBo implements Serializable {
    /**
     * 优惠券ID
     */
    private Long couponId;

    /**
     * 用户选择的优惠券使用数量
     */
    private Long couponSelectQuantity = 0L;

    /**
     * 券额(积分数,金额,折扣率等)
     */
    private BigDecimal couponAmount;

    /**
     * 当前可用优惠券项最新状态(0-未选中,1-已选中,2-不可使用)
     * UI传入和后台返回通用
     */
    private Integer status;

    /**
     * 优惠券扣除的金额
     */
    private BigDecimal couponReducedAmount;

    /**
     * 用户当前实时可用优惠券数量
     */
    private Long couponAvailableQuantity;

    private Coupon coupon;

}

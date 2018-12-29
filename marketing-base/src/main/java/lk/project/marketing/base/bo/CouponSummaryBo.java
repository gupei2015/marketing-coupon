package lk.project.marketing.base.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by gupei on 2018/9/18.
 * 用户优惠券汇总对象
 */
@Data
public class CouponSummaryBo implements Serializable {

    /**
     * 租户ID
     */
    private Integer companyId;

    /**
     * 类型(0:积分;1:满减(包括现金抵扣和折扣,参考满减规则),2:赠送赠品)
     */
    private Integer couponType;

    /**
     * 优惠券名称
     */
    private String couponName;

    /**
     * 优惠券使用规则描述
     */
    private String couponDesc;

    /**
     * 优惠券图标的URL地址
     */
    private String icon;

    /**
     * 用户优惠券汇总信息
     */
    private lk.project.marketing.base.entity.CouponSummary CouponSummary;

}

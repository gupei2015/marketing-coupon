package lk.project.marketing.base.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 待使用优惠券基本信息类
 * Created by gupei on 2018/9/18.
 */
@Data
public class UseCouponBo implements Serializable {

    /**
     * 消费优惠券(模板)ID
     */
    private Long couponId;

    /**
     * 本次消费优惠券数量
     */
    private Long couponQuantity;
}

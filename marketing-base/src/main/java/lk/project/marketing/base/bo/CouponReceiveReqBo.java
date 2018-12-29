package lk.project.marketing.base.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by gupei on 2018/10/10.
 * 优惠券发放对象
 */
@Data
public class CouponReceiveReqBo implements Serializable {

    /**
     * 优惠券模版ID，当不指定促销活动ID时，按该优惠券模版默认有效活动发券
     */
    private Long couponId;

    /**
     * 促销活动ID
     */
    private Long activityId;

    /**
     * 领券数量
     */
    private Integer requestQuantity;

    /**
     * 领券场景描述
     */
    private String sceneDesc;

    private OrderBo orderBo;

    private OrderItemBo orderItemBo;

}

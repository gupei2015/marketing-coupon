package lk.project.marketing.base.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 查询订单可用优惠券结果类
 * Created by alexlu on 2018/11/13.
 */
@Data
public class OrderAvailableCouponsRespBo extends GetAvailableCouponSelectListBo implements Serializable {
    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 订单编号
     */
    private String orderNo;
}

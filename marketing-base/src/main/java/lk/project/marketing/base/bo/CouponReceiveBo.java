package lk.project.marketing.base.bo;

import lk.project.marketing.base.entity.CouponReceive;
import lk.project.marketing.base.entity.CouponReceiveDetail;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gupei on 2018/9/18.
 * 优惠券发放对象
 */
@Data
public class CouponReceiveBo implements Serializable {

    /**
     * 租户ID
     */
    private Integer companyId;

    /**
     * 领券/发券主记录信息
     */
    private CouponReceive couponReceive;

    /**
     * 领券/发券明细信息
     */
    private List<CouponReceiveDetail> couponReceiveDetails;

}

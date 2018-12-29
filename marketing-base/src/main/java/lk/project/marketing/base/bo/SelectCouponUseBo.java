package lk.project.marketing.base.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 按模板分组的可使用优惠券列表
 * Created by alexlu on 2018/11/20.
 */
@Data
public class SelectCouponUseBo extends CouponTemplateInfoBo implements Serializable {

    /**
     * 用户此类优惠券总共剩余数量
     */
    private Long couponTotalRemainQuantity;

    /**
     * 用户此类优惠券各活动领劵记录列表
     * (点击详情后查看页面)
     */
    List<SelectCouponReceiveInfoBo> selectCouponReceiveInfoList;
}

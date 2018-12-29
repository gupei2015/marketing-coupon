package lk.project.marketing.base.bo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by alexlu on 2018/11/20.
 */
@Data
public class SelectCouponReceiveInfoBo extends CouponTemplateInfoBo implements Serializable {
    /**
     * 优惠券领劵主记录ID
     */
    private Long couponReceiveId;

    /**
     * 优惠券领劵剩余数量
     */
    private Long couponReceiveRemainQuantity;

    /**
     * 优惠券领劵记录状态(0-已领取未使用,1-已使用,2-使用中,3-已过期)
     */
    private Integer couponReceiveStatus;

    /**
     * 优惠券使用截至日期
     */
//    @JsonFormat(pattern = "yyyy-MM-dd" , timezone = "GMT+8")
    private Date couponEndDate;
}

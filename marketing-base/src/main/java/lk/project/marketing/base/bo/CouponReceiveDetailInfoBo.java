package lk.project.marketing.base.bo;

import lk.project.marketing.base.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by alexlu on 2018/11/13.
 * 领券/发券详情
 */
@Data
public class CouponReceiveDetailInfoBo extends BaseEntity<CouponReceiveDetailInfoBo> implements Serializable {
    /**
     * 优惠券编码,发券时按规则生成
     */
    private Long couponNo;

    /**
     * 券额(积分数,金额,折扣率等)
     */
    private BigDecimal couponAmount;

    /**
     * 优惠券二维码地址URL
     */
    private String couponQrCodeUrl;
}

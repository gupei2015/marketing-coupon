package lk.project.marketing.base.bo;

import lombok.Data;

import java.io.Serializable;

@Data
/**
 * 优惠券二维码编码对象
 * Created by gupei on 2018/11/15.
 */
public class QRCodeBo implements Serializable {

    /**
     * 促销活动Id
     */
    private String activityId;

    /**
     * 优惠券编码
     */
    private String couponNo;

    /**
     * 用户编号
     */
    private String userId;

    /**
     * 微信跳转链接
     */
    private String returnUrl;


}

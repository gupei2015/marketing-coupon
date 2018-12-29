package lk.project.marketing.base.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by gupei on 2018/12/06.
 * 用户优惠券详情对象
 */
@Data
public class CouponSummaryDetailBo implements Serializable {

    /**
     * 领券ID
     */
    private Long receiveId;

    /**
     * 用户ID
     */
    private String userID;

    /**
     * 用户编号
     */
    private String userCode;

    /**
     * 优惠券ID
     */
    private Long couponID;

    /**
     * 活动Id
     */
    private Long activityID;

    /**
     * 未使用数量
     */
    private Long remainQuantity;

    /**
     * 生效日期
     */
    private Date startDate;

    /**
     * 截止日期
     */
    private Date endDate;

    /**
     * 领取场景描述
     */
    private String remark;

    /**
     * 领取时间
     */
    private Timestamp createAt;

    /**
     * 优惠券编码
     */
    private String couponNo;

    /**
     * 券额
     */
    private BigDecimal couponAmount;

    /**
     * 优惠券二维码地址
     */
    private String qrCodeUrl;

}

package lk.project.marketing.client.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UpdateCouponsResultDto implements Serializable {
    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 商品ID
     */
    private String skuId;

    /**
     * 会员Dto对象
     */
    private MemberDto memberDto;

    /**
     * 使用的优惠券详情列表
     */
    private List<AvailableCouponDetailsDto> availableCouponDetailsList;
}

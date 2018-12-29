package lk.project.marketing.client.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by alexlu on 2018/11/23.
 */
@Data
public class SortAvailableMatchedCouponsReqDto implements Serializable {
    /**
     * 可用的用户优惠券列表
     */
    List<AvailableCouponDetailsDto> availableCouponDetailsDtoList;
}


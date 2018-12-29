package lk.project.marketing.client.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GetAvailableCouponDetailListDto implements Serializable {

    /**
     * 可使用的优惠券列表
     */
    private List<AvailableCouponDetailsDto> availableCouponDetailList;
}

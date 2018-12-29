package lk.project.marketing.client.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProduceCouponDto implements Serializable {

    /**
     * 领券请求Dto
     */
    private CouponReceiveReqDto couponReceiveReqDto;

    /**
     * 会员信息Dto
     */
    private MemberDto memberDto;
}

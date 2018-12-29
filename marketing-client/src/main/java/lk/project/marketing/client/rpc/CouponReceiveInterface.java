package lk.project.marketing.client.rpc;

import lk.project.marketing.client.dto.CouponReceiveReqDto;
import lk.project.marketing.client.dto.MemberDto;
import lk.project.marketing.client.vo.ResponseVO;

public interface CouponReceiveInterface {

    /**
     * 单次领券接口
     * @param couponReceiveReqDto
     * @param memberDto
     * @return
     */
    ResponseVO produceCoupon(CouponReceiveReqDto couponReceiveReqDto, MemberDto memberDto);
}

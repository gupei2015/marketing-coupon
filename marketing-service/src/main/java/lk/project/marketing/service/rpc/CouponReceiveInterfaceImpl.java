package lk.project.marketing.service.rpc;

import lk.project.marketing.base.bo.CouponReceiveReqBo;
import lk.project.marketing.base.bo.MemberBo;
import lk.project.marketing.client.dto.CouponReceiveReqDto;
import lk.project.marketing.client.dto.MemberDto;
import lk.project.marketing.client.rpc.CouponReceiveInterface;
import lk.project.marketing.client.vo.ResponseVO;
import lk.project.marketing.service.rpc.pojo.BaseResponse;
import lk.project.marketing.service.CouponReceiveService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class CouponReceiveInterfaceImpl extends BaseResponse implements CouponReceiveInterface {

    @Autowired
    CouponReceiveService couponReceiveService;

    /**
     * 单次领券接口
     * @param couponReceiveReqDto
     * @param memberDto
     * @return
     */
    @Override
    public ResponseVO produceCoupon(CouponReceiveReqDto couponReceiveReqDto, MemberDto memberDto){
        CouponReceiveReqBo couponReceiveReqBo = new CouponReceiveReqBo();
        MemberBo memberBo = new MemberBo();
        BeanUtils.copyProperties(couponReceiveReqDto,couponReceiveReqBo);
        BeanUtils.copyProperties(memberDto,memberBo);
        return getFromData(couponReceiveService.produceCoupon(couponReceiveReqBo,memberBo));
    }
}

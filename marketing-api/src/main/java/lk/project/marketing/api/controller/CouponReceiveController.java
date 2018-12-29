package lk.project.marketing.api.controller;

import lk.project.marketing.api.common.BaseController;
import lk.project.marketing.client.dto.ProduceCouponDto;
import lk.project.marketing.client.rpc.CouponReceiveInterface;
import lk.project.marketing.client.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/couponReceive")
public class CouponReceiveController extends BaseController {

    @Autowired
    CouponReceiveInterface couponReceiveInterface;

    /**
     * 单次领券接口
     * @param produceCouponDto
     * @return
     */
    @RequestMapping("/produceCoupon")
    public ResponseVO produceCoupon(@RequestBody ProduceCouponDto produceCouponDto){
        return couponReceiveInterface.produceCoupon(produceCouponDto.getCouponReceiveReqDto(),produceCouponDto.getMemberDto());
    }
}

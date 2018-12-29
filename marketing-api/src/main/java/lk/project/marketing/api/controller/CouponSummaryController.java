package lk.project.marketing.api.controller;

import lk.project.marketing.api.common.BaseController;
import lk.project.marketing.client.rpc.CouponSummaryInterface;
import lk.project.marketing.client.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/couponSummary")
public class CouponSummaryController extends BaseController {

    @Autowired
    CouponSummaryInterface couponSummaryInterface;

    /**
     * 获取用户优惠券列表
     * @param userId
     * @return
     */
    @RequestMapping("/getUserCoupon")
    public ResponseVO produceCoupon(@RequestParam String userId){
        return couponSummaryInterface.getUserCouponSummary(userId);
    }

    /**
     * 获取用户优惠券领取详情
     * @param userId    用户ID
     * @param couponId  优惠券ID
     * @return
     */
    @RequestMapping("/getUserCouponDetail")
    public ResponseVO produceCoupon(@RequestParam String userId,@RequestParam Long couponId){
        return couponSummaryInterface.getUserCouponDetail(userId,couponId);
    }
}

package lk.project.marketing.backend.api.controller;

import lk.project.marketing.backend.api.common.BaseController;
import lk.project.marketing.client.dto.CouponReqDto;
import lk.project.marketing.client.dto.QueryCouponReqDto;
import lk.project.marketing.client.rpc.CouponManagementInterface;
import lk.project.marketing.client.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/backend/couponManagement")
public class CouponManagementController extends BaseController {

    @Autowired
    CouponManagementInterface couponManagementInterface;

    /**
     * 新增优惠券模板
     * @param couponReqDto
     * @return
     */
    @PostMapping("/saveCoupon")
    public ResponseVO saveCoupon(@RequestBody CouponReqDto couponReqDto){
        return couponManagementInterface.saveCoupon(couponReqDto);
    }

    /**
     * 删除优惠券模板
     * @param couponId
     * @return
     */
    @GetMapping("/deleteCoupon")
    public ResponseVO deleteCoupon(@RequestParam Long couponId){
        return couponManagementInterface.deleteCoupon(couponId);
    }

    /**
     * 查询满足条件的优惠券模板
     * @param queryCouponReqDto
     * @return
     */
    @PostMapping("/queryCoupon")
    public ResponseVO queryCoupon(@RequestBody QueryCouponReqDto queryCouponReqDto){
        return couponManagementInterface.queryCoupon(queryCouponReqDto);
    }
}

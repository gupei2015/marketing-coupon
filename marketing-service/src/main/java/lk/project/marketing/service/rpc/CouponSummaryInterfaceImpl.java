package lk.project.marketing.service.rpc;


import lk.project.marketing.client.rpc.CouponSummaryInterface;
import lk.project.marketing.client.vo.ResponseVO;
import lk.project.marketing.service.rpc.pojo.BaseResponse;
import lk.project.marketing.service.CouponSummaryService;
import org.springframework.beans.factory.annotation.Autowired;

public class CouponSummaryInterfaceImpl extends BaseResponse implements CouponSummaryInterface {

    @Autowired
    CouponSummaryService couponSummaryService;

    /**
     * 获取用户优惠券列表
     * @param userId    用户ID
     * @return
     */
    @Override
    public ResponseVO getUserCouponSummary(String userId){
        return getFromData(couponSummaryService.getUserCouponSummary(userId));
    }

    @Override
    public ResponseVO getUserCouponDetail(String userId, Long couponId) {
        return getFromData(couponSummaryService.getUserCouponDetail(userId, couponId));
    }
}

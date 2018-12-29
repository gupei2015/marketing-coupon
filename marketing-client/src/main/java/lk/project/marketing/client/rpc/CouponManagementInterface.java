package lk.project.marketing.client.rpc;

import lk.project.marketing.client.dto.CouponReqDto;
import lk.project.marketing.client.dto.QueryCouponReqDto;
import lk.project.marketing.client.vo.ResponseVO;

/**
 * Created by alexlu on 2018/10/30.
 */
public interface CouponManagementInterface {

    /**
     * 添加或更新优惠券模板
     * @param couponReqDto
     * @return
     */
    ResponseVO saveCoupon(CouponReqDto couponReqDto);

    /**
     * 删除优惠券模板
     * @param couponId
     * @return
     */
    ResponseVO deleteCoupon(Long couponId);

    /**
     * 查询满足条件的优惠券模板
     * @param queryCouponReqDto
     * @return
     */
    ResponseVO queryCoupon(QueryCouponReqDto queryCouponReqDto);
}

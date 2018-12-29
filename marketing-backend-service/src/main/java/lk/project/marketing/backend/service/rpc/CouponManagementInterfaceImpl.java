package lk.project.marketing.backend.service.rpc;

import lk.project.marketing.backend.service.CouponManagementService;
import lk.project.marketing.backend.service.rpc.pojo.BaseResponse;
import lk.project.marketing.base.bo.CouponReqBo;
import lk.project.marketing.base.bo.QueryCouponReqBo;
import lk.project.marketing.client.dto.CouponReqDto;
import lk.project.marketing.client.dto.QueryCouponReqDto;
import lk.project.marketing.client.rpc.CouponManagementInterface;
import lk.project.marketing.client.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by alexlu on 2018/11/15.
 */
public class CouponManagementInterfaceImpl extends BaseResponse implements CouponManagementInterface {

    @Autowired
    CouponManagementService couponManagementService;

    /**
     * 添加或更新优惠券模板
     * @param couponReqDto
     */
    @Override
    public ResponseVO saveCoupon(CouponReqDto couponReqDto){
        CouponReqBo couponReqBo = new CouponReqBo();
        BeanUtils.copyProperties(couponReqDto,couponReqBo);
        return getFromData(couponManagementService.saveCoupon(couponReqBo));
    }

    /**
     * 删除优惠券模板
     * @param couponId
     * @return
     */
    @Override
    public ResponseVO deleteCoupon(Long couponId){
        return getFromData(couponManagementService.deleteCoupon(couponId));
    }

    /**
     * 查询满足条件的优惠券模板
     * @param queryCouponReqDto
     * @return
     */
    @Override
    public ResponseVO queryCoupon(QueryCouponReqDto queryCouponReqDto){
        QueryCouponReqBo queryCouponReqBo = new QueryCouponReqBo();
        BeanUtils.copyProperties(queryCouponReqDto,queryCouponReqBo);
        return getFromData(couponManagementService.queryCoupon(queryCouponReqBo));
    }
}

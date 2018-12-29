package lk.project.marketing.mapper;

/**
 * Created by Pei Gu on 2018/9/13.
 */

import com.baomidou.mybatisplus.mapper.BaseMapper;
import lk.project.marketing.base.bo.CouponReqBo;
import lk.project.marketing.base.entity.Coupon;

public interface CouponMapper extends BaseMapper<Coupon> {

    /**
     * 根据id获取优惠券模板
     * @param id
     * @return
     */
    Coupon getCouponById(Long id);

    /**
     * 根据id更新促销活动记录
     * @param coupon
     * @return
     */
    Boolean updateCouponById(Coupon coupon);
}

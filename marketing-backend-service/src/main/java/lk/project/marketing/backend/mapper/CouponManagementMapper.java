package lk.project.marketing.backend.mapper;

/**
 * Created by Pei Gu on 2018/9/13.
 */

import com.baomidou.mybatisplus.mapper.BaseMapper;
import lk.project.marketing.base.entity.Coupon;

public interface CouponManagementMapper extends BaseMapper<Coupon> {

    /**
     * 根据id获取优惠券模板
     * @param id
     * @return
     */
    Coupon getCouponById(Long id);

}

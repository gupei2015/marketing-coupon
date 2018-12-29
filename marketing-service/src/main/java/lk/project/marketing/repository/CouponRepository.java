package lk.project.marketing.repository;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lk.project.marketing.base.entity.Coupon;
import lk.project.marketing.mapper.CouponMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CouponRepository extends ServiceImpl<CouponMapper,Coupon> {

    @Autowired
    CouponMapper couponMapper;

    /**
     * 根据优惠券模板ID获取优惠券模板
     * @param id
     * @return
     */
    public Coupon getCouponById(Long id){
        return couponMapper.getCouponById(id);
    }

    /**
     * 更新优惠券模板
     * @param
     * @return
     */
    public Boolean updateCouponById(Coupon coupon){
        return couponMapper.updateCouponById(coupon);
    }
}

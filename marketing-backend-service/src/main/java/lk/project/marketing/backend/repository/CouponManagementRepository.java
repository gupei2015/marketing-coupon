package lk.project.marketing.backend.repository;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lk.project.marketing.base.entity.Coupon;
import lk.project.marketing.backend.mapper.CouponManagementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CouponManagementRepository extends ServiceImpl<CouponManagementMapper,Coupon> {

    @Autowired
    CouponManagementMapper couponManagementMapper;

    /**
     * 根据优惠券模板ID获取优惠券模板
     * @param id
     * @return
     */
    public Coupon getCouponById(Long id){
        return couponManagementMapper.getCouponById(id);
    }

}

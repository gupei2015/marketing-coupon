package lk.project.marketing.repository;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lk.project.marketing.base.constant.CommonConstants;
import lk.project.marketing.base.entity.CouponConsume;
import lk.project.marketing.mapper.CouponConsumeMapper;
import org.springframework.stereotype.Repository;

/**
 * Created by alexlu on 2018/11/7.
 */
@Repository
public class CouponConsumeRepository extends ServiceImpl<CouponConsumeMapper, CouponConsume> {

    /**
     * 根据订单ID获取优惠券用劵记录
     * @param orderId
     * @return
     */
    public CouponConsume getCouponConsumeInfoByOrderId(String orderId){
        CouponConsume filterObj = new CouponConsume();
        filterObj.setOrderId(orderId);
        filterObj.setDelete(CommonConstants.DEFAULT_VALID);
        Wrapper wrapper = new EntityWrapper(filterObj);
        return selectOne(wrapper);
    }

    /**
     * 根据用券ID得到用券记录
     * @param couponConsumeId
     * @return
     */
    public CouponConsume getCouponConsumeById(Long couponConsumeId){
        CouponConsume couponConsume = new CouponConsume();
        couponConsume.setId(couponConsumeId);
        couponConsume.setDelete(CommonConstants.DEFAULT_VALID);
        Wrapper<CouponConsume> wrapperObj = new EntityWrapper<>(couponConsume);
        return selectOne(wrapperObj);
    }

    /**
     * 逻辑删除用券记录
     * @param consumeId
     * @return
     */
    public Boolean logicDeleteCouponConsume(Long consumeId){
        CouponConsume couponConsume = new CouponConsume();
        couponConsume.setId(consumeId);
        couponConsume.setDelete(CommonConstants.DEFAULT_INVALID);
        return updateById(couponConsume);
    }
}

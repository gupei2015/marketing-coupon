package lk.project.marketing.backend.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import lk.project.marketing.backend.repository.CouponManagementRepository;
import lk.project.marketing.backend.service.CouponManagementService;
import lk.project.marketing.base.bo.CouponReqBo;
import lk.project.marketing.base.bo.PagerBaseRespBo;
import lk.project.marketing.base.bo.QueryCouponReqBo;
import lk.project.marketing.base.constant.CommonConstants;
import lk.project.marketing.base.entity.Coupon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by gupei on 2018/09/11.
 */
@Service
@Slf4j
public class CouponManagementServiceImpl implements CouponManagementService {

    @Autowired
    CouponManagementRepository couponRepository;

    /**
     * 添加或更新优惠券模板
     * @param couponRequest
     * @return
     */
    @Override
    public Boolean saveCoupon(CouponReqBo couponRequest) {
        Coupon coupon = new Coupon();
        BeanUtils.copyProperties(couponRequest,coupon);
        return couponRepository.insertOrUpdate(coupon);
    }

    /**
     * 查询满足条件的优惠券模板
     * @param queryCouponReqBo
     * @return
     */
    @Override
    public PagerBaseRespBo<Coupon> queryCoupon(QueryCouponReqBo queryCouponReqBo) {
        Coupon coupon = new Coupon();
        BeanUtils.copyProperties(queryCouponReqBo,coupon);
        coupon.setDelete(CommonConstants.DEFAULT_VALID);
        Wrapper<Coupon> wrapperObj = new EntityWrapper(coupon);
        wrapperObj.orderBy("created_at",false);
        List<Coupon> couponList;
        PagerBaseRespBo<Coupon> couponListRespBo = new PagerBaseRespBo();
        if(queryCouponReqBo.getIsPager().equals(CommonConstants.DEFAULT_QUERY_IS_PAGER)){
            Page page = new Page(queryCouponReqBo.getPageNum(),queryCouponReqBo.getPageSize());
            couponList = couponRepository.selectPage(page, wrapperObj).getRecords();
            couponListRespBo.setTotalCount(Long.valueOf(couponList.size()));
            couponListRespBo.setPageData(couponList);
            return couponListRespBo;
        }
        couponList = couponRepository.selectList(wrapperObj);
        couponListRespBo.setPageData(couponList);
        couponListRespBo.setTotalCount(Long.valueOf(couponList.size()));
        return couponListRespBo;
    }

    /**
     * 删除优惠券模板
     * @param couponId
     * @return
     */
    @Override
    public Boolean deleteCoupon(Long couponId){
        Coupon coupon = couponRepository.selectById(couponId);
        coupon.setDelete(CommonConstants.DEFAULT_INVALID);
        return couponRepository.updateById(coupon);
    }

}

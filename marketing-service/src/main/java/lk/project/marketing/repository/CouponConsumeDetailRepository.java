package lk.project.marketing.repository;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lk.project.marketing.base.constant.CommonConstants;
import lk.project.marketing.base.entity.CouponConsumeDetail;
import lk.project.marketing.mapper.CouponConsumeDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Repository
public class CouponConsumeDetailRepository extends ServiceImpl<CouponConsumeDetailMapper,CouponConsumeDetail> {

    @Autowired
    CouponConsumeDetailMapper couponConsumeDetailMapper;

    /**
     * 根据用券id获取用券详情列表
     * @param consumeId
     * @return
     */
    public List<CouponConsumeDetail> getCouponConsumeDetailListByConsumeId(Long consumeId) {
        return couponConsumeDetailMapper.getCouponConsumeDetailListByConsumeId(consumeId);
    }

    /**
     * 逻辑删除用券详情列表记录
     * @return
     */
    public Boolean logicDeleteCouponConsumeDetailList(List<CouponConsumeDetail> couponConsumeDetailList){
        if (!CollectionUtils.isEmpty(couponConsumeDetailList)) {
            for(CouponConsumeDetail couponConsumeDetail : couponConsumeDetailList){
                couponConsumeDetail.setDelete(CommonConstants.DEFAULT_INVALID);
                if (!updateById(couponConsumeDetail)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 删除用券详情列表记录
     * @param couponConsumeDetailList
     * @return
     */
    public Boolean deleteCouponConsumeDetailList(List<CouponConsumeDetail> couponConsumeDetailList){
        if (!CollectionUtils.isEmpty(couponConsumeDetailList)) {
            for(CouponConsumeDetail couponConsumeDetail : couponConsumeDetailList){
                if(!deleteById(couponConsumeDetail.getId())){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}

package lk.project.marketing.repository;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lk.project.marketing.base.bo.CouponSummaryDetailBo;
import lk.project.marketing.base.constant.CommonConstants;
import lk.project.marketing.base.enums.CouponReceiveStatusEnum;
import lk.project.marketing.base.entity.CouponReceiveDetail;
import lk.project.marketing.mapper.CouponReceiveDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Repository
public class CouponReceiveDetailRepository extends ServiceImpl<CouponReceiveDetailMapper,CouponReceiveDetail> {

    @Autowired
    CouponReceiveDetailMapper couponReceiveDetailMapper;

    /**
     * 获取优惠券指定数量的领劵明细记录
     * @param couponReceiveId
     * @param useCouponQuantity
     * @return
     */
    public List<CouponReceiveDetail> getCouponReceiveDetailListByQuantity(Long couponReceiveId, Long useCouponQuantity){
        CouponReceiveDetail filterDetailObj = new CouponReceiveDetail();
        filterDetailObj.setCouponReceiveId(couponReceiveId);
        filterDetailObj.setDelete(CommonConstants.DEFAULT_VALID);
        filterDetailObj.setStatus(CouponReceiveStatusEnum.NOT_USED.getCode());
        Wrapper wrapper = new EntityWrapper(filterDetailObj);

        return selectPage(new Page(1, useCouponQuantity.intValue()), wrapper).getRecords();
    }

    /**
     * 根据发券ID获得发券详情列表
     * @param couponReceiveId
     * @return
     */
    public List<CouponReceiveDetail> getCouponReceiveDetailListByCouponReceiveId(Long couponReceiveId) {
        CouponReceiveDetail filterObj = new CouponReceiveDetail();
        filterObj.setDelete(CommonConstants.DEFAULT_VALID);
        filterObj.setCouponReceiveId(couponReceiveId);
        Wrapper<CouponReceiveDetail> wrapperObj = new EntityWrapper<>(filterObj);
        return selectList(wrapperObj);
    }

    /**
     * 逻辑删除发券详情列表
     * @return
     */
    public Boolean logicDeleteCouponReceiveDetailList(List<CouponReceiveDetail> couponReceiveDetailList){
        if(!CollectionUtils.isEmpty(couponReceiveDetailList)){
            for(CouponReceiveDetail couponReceiveDetail : couponReceiveDetailList){
                couponReceiveDetail.setDelete(CommonConstants.DEFAULT_INVALID);
                if (!updateById(couponReceiveDetail)) return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 删除发券详情列表记录
     * @param couponReceiveDetailList
     * @return
     */
    public Boolean deleteCouponReceiveDetailList(List<CouponReceiveDetail> couponReceiveDetailList){
        if (!CollectionUtils.isEmpty(couponReceiveDetailList)) {
            for(CouponReceiveDetail couponReceiveDetail : couponReceiveDetailList){
                if (! deleteById(couponReceiveDetail)) return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 查询用户优惠券详情
     * @param couponId  优惠券ID
     * @param userId    用户Id
     * @return
     */
    public List<CouponSummaryDetailBo> getUserCouponReceiveDetailListByCouponId(Long couponId, String userId) {
        return couponReceiveDetailMapper.getUserCouponReceiveDetailListByCouponId(couponId,userId);
    }
}

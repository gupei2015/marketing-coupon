package lk.project.marketing.repository;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lk.project.marketing.base.constant.CommonConstants;
import lk.project.marketing.client.exception.BusinessErrorCodeEnum;
import lk.project.marketing.base.enums.CouponReceiveStatusEnum;
import lk.project.marketing.base.entity.*;
import lk.project.marketing.client.exception.BusinessException;
import lk.project.marketing.mapper.CouponSummaryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class CouponWithdrawRepository extends ServiceImpl<CouponSummaryMapper, CouponSummary> {
    private final static Logger log = LoggerFactory.getLogger(CouponWithdrawRepository.class);

    @Autowired
    CouponSummaryRepository couponSummaryRepository;

    @Autowired
    CouponReceiveRepository couponReceiveRepository;

    @Autowired
    CouponReceiveDetailRepository couponReceiveDetailRepository;

    @Autowired
    CouponConsumeRepository couponConsumeRepository;

    @Autowired
    CouponConsumeDetailRepository couponConsumeDetailRepository;

    /**
     * 用户退款后回滚用户使用的优惠券记录(逻辑删除)
     * @param transactionId
     * @param transactionType
     */
    public void withdrawCouponReceive(Long transactionId, Integer transactionType){
        withdrawCouponReceive(transactionId, transactionType, true);
    }

    /**
     *用户退款后回滚用户使用的优惠券记录
     * @param transactionId     优惠券流水主记录Id
     * @param transactionType   0：领券；1：用券
     * @param logicDelete       物理删除标准
     */
    public void withdrawCouponReceive(Long transactionId, Integer transactionType, Boolean logicDelete){

        CouponSummary couponSummaryFilter = new CouponSummary();
        if(transactionType==0){
            CouponReceive couponReceive = couponReceiveRepository.getCouponReceiveById(transactionId);
            if(couponReceive==null){
                throw new BusinessException(BusinessErrorCodeEnum.NOT_FOUND_COUPON_INFO);
            }

            couponSummaryFilter.setCouponId(couponReceive.getCouponId());
            couponSummaryFilter.setDelete(CommonConstants.DEFAULT_VALID);
            CouponSummary couponSummary = couponSummaryRepository.getCouponSummary(couponSummaryFilter);
            if(couponSummary==null){
                throw new BusinessException(BusinessErrorCodeEnum.NOT_FOUND_USER_COUPON_SUMMARY_RECORD);
            }
            if(couponSummary.getCouponQuantity()==null || couponSummary.getCouponQuantity()<=0){
                throw new BusinessException(BusinessErrorCodeEnum.COUPON_SUMMARY_FIELD_EXCEPTION);
            }
            if(!CouponReceiveStatusEnum.NOT_USED.getCode().equals(couponReceive.getStatus())){
                throw new BusinessException(BusinessErrorCodeEnum.COUPON_RECEIVE_STATUS_EXCEPTION);
            }
            if(couponReceive.getReceiveQuantity()==null || couponReceive.getReceiveQuantity()<=0){
                throw new BusinessException(BusinessErrorCodeEnum.COUPON_RECEIVE_FIELD_EXCEPTION);
            }
            couponSummary.setCouponQuantity(couponSummary.getCouponQuantity()-couponReceive.getReceiveQuantity());
            couponSummary.setReceivedQuantity(couponSummary.getReceivedQuantity()-couponReceive.getReceiveQuantity());
            List<CouponReceiveDetail> couponReceiveDetailList = couponReceiveDetailRepository
                    .getCouponReceiveDetailListByCouponReceiveId(transactionId);
            couponSummaryRepository.updateById(couponSummary);
            if(logicDelete){
                couponReceiveDetailRepository.logicDeleteCouponReceiveDetailList(couponReceiveDetailList);
                couponReceiveRepository.logicDeleteCouponReceive(couponReceive.getId());
            }else {
                couponReceiveDetailRepository.deleteCouponReceiveDetailList(couponReceiveDetailList);
                couponReceiveRepository.deleteById(couponReceive.getId());
            }
        }
        else if(transactionType==1){
            CouponConsume couponConsume = couponConsumeRepository.getCouponConsumeById(transactionId);
            if(couponConsume==null){
                throw new BusinessException(BusinessErrorCodeEnum.NOT_FOUND_USER_COUPON_CONSUME_RECORD);
            }
            couponSummaryFilter.setCouponId(couponConsume.getCouponId());
            couponSummaryFilter.setDelete(CommonConstants.DEFAULT_VALID);
            CouponSummary couponSummary = couponSummaryRepository.getCouponSummary(couponSummaryFilter);
            if(couponSummary==null){
                throw new BusinessException(BusinessErrorCodeEnum.NOT_FOUND_USER_COUPON_SUMMARY_RECORD);
            }
            if(couponSummary.getCouponQuantity()==null || couponSummary.getCouponQuantity()<=0){
                throw new BusinessException(BusinessErrorCodeEnum.COUPON_SUMMARY_FIELD_EXCEPTION);
            }
            if(couponConsume.getConsumeQuantity()==null || couponConsume.getConsumeQuantity()<=0){
                throw new BusinessException(BusinessErrorCodeEnum.COUPON_CONSUME_FIELD_EXCEPTION);
            }
            couponSummary.setCouponQuantity(couponSummary.getCouponQuantity()+couponConsume.getConsumeQuantity());
            couponSummary.setConsumedQuantity(couponSummary.getConsumedQuantity()-couponConsume.getConsumeQuantity());
            couponSummaryRepository.updateById(couponSummary);
            List<CouponConsumeDetail> couponConsumeDetailList = couponConsumeDetailRepository
                    .getCouponConsumeDetailListByConsumeId(couponConsume.getId());
            if(logicDelete){
                couponConsumeDetailRepository.logicDeleteCouponConsumeDetailList(couponConsumeDetailList);
                couponConsumeRepository.logicDeleteCouponConsume(couponConsume.getId());
            }else{
                couponConsumeDetailRepository.deleteCouponConsumeDetailList(couponConsumeDetailList);
                couponConsumeRepository.deleteById(couponConsume.getId());
            }
        }
    }

}

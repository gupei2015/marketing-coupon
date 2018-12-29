package lk.project.marketing.service;


import lk.project.marketing.base.entity.CouponReceive;
import lk.project.marketing.base.entity.CouponReceiveDetail;

import java.util.List;

/**
 * Created by zhanghongda on 2018/10/31.
 */
public interface CouponReceiveDetailService {

    /**
     * 根据领券/发券业务请求信息对象生成领券明细信息
     * @param couponReceive 领券主记录信息
     * @return 领券明细对象数据集
     */
    List<CouponReceiveDetail> buildCouponReceiveDetails(CouponReceive couponReceive);

    /**
     * 根据领券明细序列号生成优惠券码
     * @param sequenceId    明细序列号
     * @return 优惠券码
     */
    String generateCouponNo(Long sequenceId);

    /**
     * 根据领券信息生成优惠券二维码
     * @param couponReceive 领券主记录信息
     * @param couponReceiveDetail 领券明细记录信息
     */
    void generateQrCode(CouponReceive couponReceive, CouponReceiveDetail couponReceiveDetail);

}

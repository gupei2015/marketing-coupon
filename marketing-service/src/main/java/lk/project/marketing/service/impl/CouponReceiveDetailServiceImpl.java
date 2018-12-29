package lk.project.marketing.service.impl;


import com.google.gson.Gson;
import lk.project.marketing.base.bo.QRCodeBo;
import lk.project.marketing.base.enums.CouponReceiveStatusEnum;
import lk.project.marketing.config.StorageConfig;
import lk.project.marketing.base.entity.CouponReceive;
import lk.project.marketing.base.entity.CouponReceiveDetail;
import lk.project.marketing.service.CouponReceiveDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.LongStream;

/**
 * Created by zhanghongda on 2018/10/31.
 */
@Service
public class CouponReceiveDetailServiceImpl implements CouponReceiveDetailService {

    private final static Logger log = LoggerFactory.getLogger(CouponReceiveDetailServiceImpl.class);

    @Autowired
    private StorageConfig storageConfig;

    /**
     * 根据领券/发券业务请求信息对象生成领券明细信息
     * @param couponReceive 领券主记录信息
     * @return 领券明细对象数据集
     */
    @Override
    public List<CouponReceiveDetail> buildCouponReceiveDetails(CouponReceive couponReceive) {
        List<CouponReceiveDetail> receiveDetails = new ArrayList();

        LongStream.range(0, couponReceive.getReceiveQuantity())
            .forEach(o -> {
                CouponReceiveDetail receiveDetail = new CouponReceiveDetail();
                receiveDetail.setCouponAmount(couponReceive.getCouponAmount());
                receiveDetail.setCouponNo(generateCouponNo(o));
                receiveDetail.setStatus(CouponReceiveStatusEnum.NOT_USED.getCode());
                generateQrCode(couponReceive,receiveDetail);
                receiveDetails.add(receiveDetail);
            });

        return receiveDetails;
    }

    /**
     * 根据领券明细序列号生成优惠券码
     * @param sequenceId    明细序列号
     * @return 优惠券码
     */
    @Override
    public String generateCouponNo(Long sequenceId) {

        int hashCode = UUID.randomUUID().hashCode();
        hashCode = hashCode<0? -hashCode:hashCode;

        StringBuilder sbCoupon = new StringBuilder();
        SimpleDateFormat sf = new SimpleDateFormat("YYYYMMdd");

        return sbCoupon.append(sf.format(new Date()))
                .append(String.format("%04d", sequenceId))
                .append(String.format("%010d", hashCode))
                .toString();
    }

    /**
     * 根据领券信息生成优惠券二维码
     * @param couponReceive 领券主记录信息
     * @param couponReceiveDetail 领券明细记录信息
     */
    @Override
    public void generateQrCode(CouponReceive couponReceive,CouponReceiveDetail couponReceiveDetail) {
        QRCodeBo qrCodeBo = new QRCodeBo();
        qrCodeBo.setActivityId(String.valueOf(couponReceive.getActivityId()));

        qrCodeBo.setCouponNo(couponReceiveDetail.getCouponNo());
        qrCodeBo.setUserId(couponReceive.getUserId());

        Gson gson = new Gson();
        String qrCodeText = gson.toJson(qrCodeBo);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        try {
//            ImageIO.write(MatrixToImage.writeInfoToJpgBuff(qrCodeText), CommonConstants.IMAGE_TYPE_JPEG, baos);
//        } catch (Exception e) {
//            log.error(BusinessErrorCodeEnum.COUPON_RECEIVE_DETAIL_QR_CODE_GENERATE_FAILED.getMessage(), e);
//        }

        //二维码图片上传 & 信息存储
//        InputStream qrCodeImgStream = new ByteArrayInputStream(baos.toByteArray());
//        UploadUtils.setAmazonS3(storageConfig);
//        String qrCodeImgUploadUrl = UploadUtils.uploadFile(storageConfig.getBaseUrl(), qrCodeImgStream,
//                        storageConfig.getBucketNamePrefix().toLowerCase() + CommonConstants.TAG_DELIMITER
//                                     + couponReceiveDetail.getCouponNo().toLowerCase(),
//                                     String.format("%s-%s-%s-%s-%s.jpg", couponReceive.getId(), couponReceive.getActivityId(),
//                                     couponReceive.getUserId(), couponReceiveDetail.getId(),
//                                     qrCodeBo.getCouponNo()));
//        couponReceiveDetail.setCouponQrCodeUrl(qrCodeImgUploadUrl);
    }
}

package lk.project.marketing.service.impl;

import lk.project.marketing.base.bo.CouponReceiveBo;
import lk.project.marketing.base.bo.CouponReceiveReqBo;
import lk.project.marketing.base.bo.MemberBo;
import lk.project.marketing.client.exception.BusinessException;
import lk.project.marketing.init.MarketingServiceApplication;
import lk.project.marketing.repository.CouponReceiveRepository;
import lk.project.marketing.service.CouponReceiveService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.stream.IntStream;

/**
 * Created by gu pei on 2018/11/18.
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MarketingServiceApplication.class)
@FixMethodOrder( MethodSorters.NAME_ASCENDING)
public class CouponReceiveServiceImplTest {

    @Resource
    CouponReceiveService couponReceiveService;

    @Resource
    CouponReceiveRepository couponReceiveRepository;

    @Test
    public void test001ProduceCoupon(){

        MemberBo memberBo = new MemberBo();
        memberBo.setUserId("1");
        memberBo.setUserCode("LK-User01");
        memberBo.setAge(21);
        memberBo.setGender(0);
        memberBo.setLevel(2);
        memberBo.setPhoneNumber("13915862136");
        memberBo.setExpValue(25000L);
        memberBo.setUserName("Test1");

        CouponReceiveReqBo couponReceiveReqBo = new CouponReceiveReqBo();
        couponReceiveReqBo.setActivityId(2l);
        couponReceiveReqBo.setCouponId(2l);
        couponReceiveReqBo.setRequestQuantity(5);
        couponReceiveReqBo.setSceneDesc("测试领取2元优惠券");
        CouponReceiveBo couponReceiveBo= couponReceiveService.produceCoupon(couponReceiveReqBo, memberBo);
        Assert.assertFalse(CollectionUtils.isEmpty(couponReceiveBo.getCouponReceiveDetails()));
        Assert.assertEquals(5, couponReceiveBo.getCouponReceiveDetails().size());

        //TODO: remove receive coupon records
        log.info("Test receive coupon finished");
    }

    @Test
    public void test002ReceivePoints(){

        MemberBo memberBo = new MemberBo();
        memberBo.setUserId("1");
        memberBo.setUserCode("LK-User01");
        memberBo.setLevel(2);
        memberBo.setUserName("Test1");

        CouponReceiveReqBo couponReceiveReqBo = new CouponReceiveReqBo();
        couponReceiveReqBo.setActivityId(21l);
        couponReceiveReqBo.setRequestQuantity(10);
        couponReceiveReqBo.setSceneDesc("测试抢红包");

        IntStream.range(0,10).parallel()
                .forEach(o->{
                    try {
                        CouponReceiveBo couponReceiveBo = couponReceiveService.produceCoupon(couponReceiveReqBo, memberBo);
                        Assert.assertTrue(CollectionUtils.isEmpty(couponReceiveBo.getCouponReceiveDetails()));
                    }
                    catch (BusinessException e){
                        log.error("Receive thread [{}] failed! {}", o, e);
                    }

                });


        //TODO: remove receive coupon records
        log.info("Test receive points finished");
    }
}
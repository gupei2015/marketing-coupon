package lk.project.marketing.repository;

import lk.project.marketing.init.MarketingServiceApplication;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MarketingServiceApplication.class)
@FixMethodOrder( MethodSorters.NAME_ASCENDING)
public class CouponWithdrawRepositoryTest {
    private final static Logger log = LoggerFactory.getLogger(CouponWithdrawRepositoryTest.class);

    @Resource
    CouponWithdrawRepository couponWithdrawRepository;

    /**
     * 用户退款后回滚用户使用的优惠券记录(逻辑删除)
     */
    @Test
    public void test001WithdrawCouponReceive(){
        couponWithdrawRepository.withdrawCouponReceive(95L,0);
    }

    /**
     * 用户退款后回滚用户使用的优惠券记录
     */
    @Test
    public void test002WithdrawCouponReceive(){
        try {
            couponWithdrawRepository.withdrawCouponReceive(95L,0,false);
        } catch (Exception e) {
            log.error("校验回滚优惠券记录失败",e);
        }
    }
}

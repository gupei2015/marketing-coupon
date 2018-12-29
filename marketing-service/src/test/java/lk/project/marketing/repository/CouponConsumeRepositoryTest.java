package lk.project.marketing.repository;

import lk.project.marketing.base.entity.CouponConsume;
import lk.project.marketing.init.MarketingServiceApplication;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MarketingServiceApplication.class)
public class CouponConsumeRepositoryTest {

    private final static Logger log = LoggerFactory.getLogger(CouponConsumeRepositoryTest.class);

    @Autowired
    CouponConsumeRepository couponConsumeRepository;

    private final static Long id = 11L;
    private CouponConsume couponConsume = new CouponConsume();

    @Before
    public void initCouponConsume(){
        couponConsume.setId(id);
        couponConsume.setUserId("2");
        couponConsume.setUserCode("wangLei");
        couponConsume.setShopId("8");
        couponConsume.setCouponId(5L);
        couponConsume.setOrderId("2");
        couponConsume.setOrderNo("10010");
        couponConsume.setOriginalAmount(new BigDecimal(1100));
        couponConsume.setCouponAmount(new BigDecimal(20));
        couponConsume.setFinalPayAmount(new BigDecimal(1080));
        couponConsume.setStatus(1);
    }

    /**
     * 根据订单ID获取优惠券用劵记录
     */
    @Test
    public void testGetCouponConsumeInfoByOrderId(){
        try {
            CouponConsume couponConsumeInfoByOrderId = couponConsumeRepository.getCouponConsumeInfoByOrderId("2");
            if(couponConsumeInfoByOrderId!=null){
                Assert.assertNotNull(couponConsumeInfoByOrderId.getUserId());
                Assert.assertNotNull(couponConsumeInfoByOrderId.getUserCode());
                Assert.assertNotNull(couponConsumeInfoByOrderId.getShopId());
                Assert.assertNotNull(couponConsumeInfoByOrderId.getCouponId());
                Assert.assertNotNull(couponConsumeInfoByOrderId.getOrderNo());
                Assert.assertNotNull(couponConsumeInfoByOrderId.getOriginalAmount());
                Assert.assertNotNull(couponConsumeInfoByOrderId.getCouponAmount());
                Assert.assertNotNull(couponConsumeInfoByOrderId.getFinalPayAmount());
                Assert.assertEquals((long)couponConsumeInfoByOrderId.getStatus(),1L);
            }
        } catch (Exception e) {
            log.error("校验查询记录失败",e);
        }
    }

    /**
     * 新增用券记录
     */
    @Test
    public void testInsertCouponConsume(){
        try {
            Boolean result = couponConsumeRepository.insert(couponConsume);
            Assert.assertTrue(result);
        } catch (Exception e) {
            log.error("校验新增用券记录失败",e);
        }
    }

    /**
     * 删除测试数据
     */
    @Test
    public void testDeleteCouponConsume(){
        try {
            boolean result = couponConsumeRepository.deleteById(couponConsume.getId());
            Assert.assertTrue(result);
        } catch (Exception e) {
            log.error("校验删除测试数据失败",e);
        }
    }
}

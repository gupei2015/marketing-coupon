package lk.project.marketing.repository;

import lk.project.marketing.base.entity.Coupon;
import lk.project.marketing.init.MarketingServiceApplication;
import org.junit.*;
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
public class CouponRepositoryTest {

    private final static Logger log = LoggerFactory.getLogger(CouponRepositoryTest.class);

    @Resource
    private CouponRepository couponRepository;

    private static Coupon coupon;

    @BeforeClass
    public static void initCoupon(){
        coupon = new Coupon();
        coupon.setAccountRuleId(2L);
        coupon.setCouponDesc("购买满800元使用");
        coupon.setCouponName("满减优惠券H");
        coupon.setCouponType(1);
        coupon.setCompanyId(1);
        coupon.setStatus(0);
    }

    /**
     * 增加一个优惠券模板
     */
    @Test
    public void test001InsertCoupon(){
        try {
            Boolean result = couponRepository.insert(coupon);
            Assert.assertTrue(result);
        } catch (Exception e) {
            log.error("校验增加优惠券模板失败",e);
        }
    }

    /**
     * 通过优惠券模板id获得优惠券模板实体
     */
    @Test
    public void test002GetCouponById(){
        Coupon couponB = null;
        try {
            couponB = couponRepository.getCouponById(coupon.getId());
            if(couponB!=null){
                Assert.assertNotNull(couponB.getAccountRuleId());
                Assert.assertNotNull(couponB.getCouponName());
                Assert.assertNotNull(couponB.getCouponType());
            }
        } catch (Exception e) {
            log.error("校验查询记录失败",e);
        }
    }

    /**
     * 通过优惠券模板id逻辑删除优惠券模板
     */
    @Test
    public void test003UpdateCouponById(){
        try {
            coupon.setShopId("3");
            Boolean result = couponRepository.updateCouponById(coupon);
            Assert.assertTrue(result);
        } catch (Exception e) {
            log.error("校验逻辑删除记录失败",e);
        }
    }

    /**
     * 删除优惠券模板记录
     */
    @Test
    public void test099DeleteCoupon(){
        try {
            boolean result = couponRepository.deleteById(coupon.getId());
            Assert.assertTrue(result);
        } catch (Exception e) {
            log.error("校验删除记录失败",e);
        }
    }
}

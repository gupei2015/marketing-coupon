package lk.project.marketing.repository;

import lk.project.marketing.base.bo.CouponHistoryInfoBo;
import lk.project.marketing.base.entity.CouponSummary;
import lk.project.marketing.init.MarketingServiceApplication;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MarketingServiceApplication.class)
@FixMethodOrder( MethodSorters.NAME_ASCENDING)
public class CouponSummaryRepositoryTest {

    private final static Logger log = LoggerFactory.getLogger(CouponSummaryRepositoryTest.class);

    @Resource
    private CouponSummaryRepository couponSummaryRepository;

    private static CouponSummary couponSummary;

    @BeforeClass
    public static void initCouponSummary(){
        couponSummary = new CouponSummary();
        couponSummary.setUserId("6");
        couponSummary.setUserCode("dongFangBuBai");
        couponSummary.setCouponId(2L);
        couponSummary.setReceivedQuantity(5L);
        couponSummary.setCouponQuantity(2L);
    }

    /**
     * 新增优惠券汇总
     */
    @Test
    public void test001InsertCouponSummary(){
        try {
            Boolean result = couponSummaryRepository.insert(couponSummary);
            Assert.assertTrue(result);
        } catch (Exception e) {
            log.error("校验新增优惠券汇总失败",e);
        }
    }

    /**
     * 查询用户优惠券汇总详情
     */
    @Test
    public void test002GetUserCouponSummaryInfo(){
        try {
            CouponSummary testCouponSummary = couponSummaryRepository.getUserCouponSummaryInfo(couponSummary.getUserId(),
                                              couponSummary.getCouponId());
            Assert.assertNotNull(testCouponSummary);
            Assert.assertEquals( couponSummary.getReceivedQuantity(), testCouponSummary.getReceivedQuantity());
        } catch (Exception e) {
            log.error("校验查询用户优惠券汇总详情失败",e);
        }
    }

    /**
     * 查询优惠券历史详情记录
     */
    @Test
    public void test003GetCouponHistoryDetails(){
        try {
            List<CouponHistoryInfoBo> couponHistoryDetails = couponSummaryRepository.getCouponHistoryDetails(
                    couponSummary.getUserId(), "1");
            Assert.assertFalse(CollectionUtils.isEmpty(couponHistoryDetails));

        } catch (Exception e) {
            log.error("校验查询优惠券历史详情记录失败",e);
        }
    }

    /**
     * 更新优惠券汇总记录
     */
    @Test
    public void test004UpdateCouponSummary(){
        couponSummary.setRewardAmount(new BigDecimal(30));
        try {
            Boolean result = couponSummaryRepository.updateById(couponSummary);
            Assert.assertTrue(result);
        } catch (Exception e) {
            log.error("校验更新优惠券汇总记录失败",e);
        }
    }

    /**
     * 查询指定用户优惠券汇总信息
     */
    @Test
    public void test005QueryUserCoupons(){
        try {
            List<CouponSummary> couponSummaryList = couponSummaryRepository.queryUserCoupons("2");
            if(!CollectionUtils.isEmpty(couponSummaryList)){
                Assert.assertEquals(3,couponSummaryList.size());
            }
        } catch (Exception e) {
            log.error("校验查询指定用户优惠券汇总信息失败",e);
        }
    }

    /**
     * 删除测试数据
     */
    @Test
    public void test099DeleteCouponSummary(){
        try {
            boolean result = couponSummaryRepository.deleteById(couponSummary.getId());
            Assert.assertTrue(result);
        } catch (Exception e) {
            log.error("校验删除测试数据失败",e);
        }
    }
}

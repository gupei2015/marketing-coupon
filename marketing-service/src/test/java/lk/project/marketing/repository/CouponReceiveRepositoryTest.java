package lk.project.marketing.repository;

import lk.project.marketing.base.bo.CouponReceiveBo;
import lk.project.marketing.base.entity.CouponReceive;
import lk.project.marketing.base.entity.CouponReceiveDetail;
import lk.project.marketing.init.MarketingServiceApplication;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MarketingServiceApplication.class)
@FixMethodOrder( MethodSorters.NAME_ASCENDING)
@Transactional
@Rollback(false)
public class CouponReceiveRepositoryTest {

    private final static Logger log = LoggerFactory.getLogger(CouponReceiveRepositoryTest.class);

    @Resource
    private CouponReceiveRepository couponReceiveRepository;

    private static CouponReceive couponReceive;

    @BeforeClass
    public static void initCouponReceive(){
        couponReceive = new CouponReceive();
        couponReceive.setUserId("1");
        couponReceive.setUserCode("LK-User01");
        couponReceive.setActivityId(25L);
        couponReceive.setCouponId(13L);
        couponReceive.setCouponAmount(new BigDecimal(10));
        couponReceive.setReceiveQuantity(2L);
        couponReceive.setRemainQuantity(2L);
        couponReceive.setStatus(0);
    }

    /**
     * 新增发券记录
     */
    @Test
    public void testInsertCouponReceive(){
        try {
            boolean result = couponReceiveRepository.insert(couponReceive);
            Assert.assertTrue(result);
        } catch (Exception e) {
            log.error("校验新增发券记录失败",e);
        }
    }

    /**
     * 根据发券id获得发券记录
     */
    @Test
    public void testGetCouponReceiveById(){
        try {
            CouponReceive receive = couponReceiveRepository.getCouponReceiveById(couponReceive.getId());
            if(receive!=null){
                Assert.assertNotNull(receive.getUserId());
                Assert.assertNotNull(receive.getUserCode());
                Assert.assertNotNull(receive.getActivityId());
                Assert.assertNotNull(receive.getCouponId());
                Assert.assertNotNull(receive.getReceiveQuantity());
                Assert.assertNotNull(receive.getStatus());
            }
        } catch (Exception e) {
            log.error("校验查询发券记录失败",e);
        }
    }

    /**
     * 根据发券id列表获得促销活动id列表
     */
    @Test
    public void testGetCouponActivityIdsByReceiveIds(){
        List<Long> couponReceiveIds = new ArrayList<>();
        couponReceiveIds.add(1L);
        couponReceiveIds.add(2L);
        try {
            List<Long> list = couponReceiveRepository.getCouponActivityIdsByReceiveIds(couponReceiveIds);
            if(!CollectionUtils.isEmpty(list)){
                Assert.assertEquals(list.size(),2);
            }
        } catch (Exception e) {
            log.error("校验查询促销活动id列表失败",e);
        }

    }

    /**
     * 查询用户可用的领券清单数据(按优惠券截止使用日期由近及远排序)
     */
    @Test
    public void testGetUserCouponReceiveList(){
        try {
            List<CouponReceive> receiveList = couponReceiveRepository.getUserCouponReceiveList("1", null, 13L);
            if(!CollectionUtils.isEmpty(receiveList)){
                Assert.assertEquals(receiveList.size(),2);
                for(CouponReceive couponReceive:receiveList){
                    Assert.assertNotNull(couponReceive.getActivityId());
                    Assert.assertNotNull(couponReceive.getReceiveQuantity());
                    Assert.assertNotNull(couponReceive.getStatus());
                }
            }
        } catch (Exception e) {
            log.error("校验查询记录失败",e);
        }

    }

    /**
     * 删除测试数据
     */
    @Test
    public void testDeleteCouponReceive(){
        try {
            boolean result = couponReceiveRepository.deleteById(couponReceive.getId());
            Assert.assertTrue(result);
        } catch (Exception e) {
            log.error("校验删除测试数据失败",e);
        }
    }

    /**
     * 新增领券相关信息
     */
    @Test
    public void testSaveCouponReceiveBo(){
        CouponReceiveBo couponReceiveBo = new CouponReceiveBo();
        List<CouponReceiveDetail> couponReceiveDetailList = new ArrayList<>();
        couponReceive.setId(95L);
        for(int i = 1; i<=couponReceive.getReceiveQuantity(); i++){
            CouponReceiveDetail couponReceiveDetail = new CouponReceiveDetail();
            couponReceiveDetail.setCouponNo("test"+i);
            couponReceiveDetail.setCouponAmount(couponReceive.getCouponAmount());
            couponReceiveDetail.setCouponQrCodeUrl("testUrl"+i);
            couponReceiveDetailList.add(couponReceiveDetail);
        }
        couponReceiveBo.setCouponReceive(couponReceive);
        couponReceiveBo.setCouponReceiveDetails(couponReceiveDetailList);
        try {
            Boolean result = couponReceiveRepository.saveCouponReceiveBo(couponReceiveBo);
            Assert.assertTrue(result);
        } catch (Exception e) {
            log.error("校验新增领券相关信息失败",e);
        }
    }

    /**
     * 获得指定用户某次活动领取优惠券数量
     */
    @Test
    public void testGetReceivedCouponQuantities(){
        try {
            Long receivedCouponQuantities = couponReceiveRepository.getReceivedCouponQuantities("2", 10L);
            Assert.assertEquals((long)receivedCouponQuantities, 6);
        } catch (Exception e) {
            log.error("校验查询领券数量失败",e);
        }
    }
}

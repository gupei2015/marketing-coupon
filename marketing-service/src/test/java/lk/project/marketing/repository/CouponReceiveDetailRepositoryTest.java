package lk.project.marketing.repository;

import lk.project.marketing.base.bo.CouponSummaryDetailBo;
import lk.project.marketing.base.entity.CouponReceiveDetail;
import lk.project.marketing.init.MarketingServiceApplication;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class CouponReceiveDetailRepositoryTest {

    private final static Logger log = LoggerFactory.getLogger(CouponReceiveDetailRepositoryTest.class);

    @Resource
    private CouponReceiveDetailRepository couponReceiveDetailRepository;

    private final static Long id = 78L;
    private CouponReceiveDetail couponReceiveDetail = new CouponReceiveDetail();

    @Before
    public void initCouponReceiveDetail(){
        couponReceiveDetail.setId(id);
        couponReceiveDetail.setCouponReceiveId(15L);
        couponReceiveDetail.setCouponNo("12580");
        couponReceiveDetail.setCouponAmount(new BigDecimal(30));
    }

    /**
     * 新增发券详情记录
     */
    @Test
    public void testInsertCouponReceiveDetail(){
        try {
            Boolean result = couponReceiveDetailRepository.insert(couponReceiveDetail);
            Assert.assertTrue(result);
        } catch (Exception e) {
            log.error("校验新增发券详情记录失败",e);
        }
    }

    /**
     * 根据发券id获得发券详情列表
     */
    @Test
    public void testGetCouponReceiveDetailListByCouponReceiveId(){
        try {
            List<CouponReceiveDetail> list = couponReceiveDetailRepository.getCouponReceiveDetailListByCouponReceiveId(15L);
            if(!CollectionUtils.isEmpty(list)){
                Assert.assertEquals(list.size(),2);
                for(CouponReceiveDetail couponReceiveDetail:list){
                    Assert.assertNotNull(couponReceiveDetail.getCouponNo());
                    Assert.assertNotNull(couponReceiveDetail.getCouponAmount());
                }
            }
        } catch (Exception e) {
            log.error("校验查询发券详情列表失败",e);
        }
    }

    /**
     * 查询用户优惠券详情
     */
    @Test
    public void testGetUserCouponReceiveDetailListByCouponId(){
        List<CouponSummaryDetailBo> userCouponReceiveDetailList = couponReceiveDetailRepository
                .getUserCouponReceiveDetailListByCouponId(19L, "3");
        if(!CollectionUtils.isEmpty(userCouponReceiveDetailList)){
            Assert.assertEquals(5,userCouponReceiveDetailList.size());
        }
    }

    /**
     * 删除测试数据
     */
    @Test
    public void testDeleteCouponReceiveDetail(){
        try {
            boolean result = couponReceiveDetailRepository.deleteById(couponReceiveDetail.getId());
            Assert.assertTrue(result);
        } catch (Exception e) {
            log.error("校验删除测试数据失败",e);
        }

    }
}

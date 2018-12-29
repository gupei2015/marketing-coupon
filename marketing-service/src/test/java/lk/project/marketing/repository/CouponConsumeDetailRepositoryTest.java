package lk.project.marketing.repository;

import lk.project.marketing.base.entity.CouponConsumeDetail;
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
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MarketingServiceApplication.class)
public class CouponConsumeDetailRepositoryTest {

    private final static Logger log = LoggerFactory.getLogger(CouponConsumeDetailRepositoryTest.class);

    @Resource
    private CouponConsumeDetailRepository couponConsumeDetailRepository;

    private final static Long id = 11L;
    private CouponConsumeDetail couponConsumeDetail = new CouponConsumeDetail();

    @Before
    public void initCouponConsumeDetail(){
        couponConsumeDetail.setId(id);
        couponConsumeDetail.setCouponConsumeId(2L);
        couponConsumeDetail.setCouponReceiveId(15L);
        couponConsumeDetail.setCouponReceiveDetailIds("45");
        couponConsumeDetail.setActivityId(18L);
    }

    /**
     * 根据用券id获得用券详情列表
     */
    @Test
    public void testGetCouponConsumeDetailListByConsumeId(){
        try {
            List<CouponConsumeDetail> list = couponConsumeDetailRepository.getCouponConsumeDetailListByConsumeId(2L);
            if(!CollectionUtils.isEmpty(list)){
                Assert.assertEquals(list.size(),1);
                for(CouponConsumeDetail couponConsumeDetail:list){
                    Assert.assertNotNull(couponConsumeDetail.getCouponConsumeId());
                    Assert.assertNotNull(couponConsumeDetail.getCouponReceiveId());
                    Assert.assertNotNull(couponConsumeDetail.getCouponReceiveDetailIds());
                    Assert.assertNotNull(couponConsumeDetail.getActivityId());
                }
            }
        } catch (Exception e) {
            log.error("校验查询用券详情列表失败",e);
        }
    }

    /**
     * 新增用券详情记录
     */
    @Test
    public void testInsertCouponConsumeDetail(){
        try {
            Boolean result = couponConsumeDetailRepository.insert(couponConsumeDetail);
            Assert.assertTrue(result);
        } catch (Exception e) {
            log.error("校验新增用券详情记录失败",e);
        }
    }

    /**
     * 删除测试数据
     */
    @Test
    public void testDeleteCouponConsumeDetail(){
        try {
            boolean result = couponConsumeDetailRepository.deleteById(couponConsumeDetail.getId());
            Assert.assertTrue(result);
        } catch (Exception e) {
            log.error("校验删除测试数据失败",e);
        }
    }
}

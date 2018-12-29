package lk.project.marketing.repository;

import lk.project.marketing.base.entity.PromotionActivity;
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
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MarketingServiceApplication.class)
public class ActivityRepositoryTest {

    private final static Logger log = LoggerFactory.getLogger(ActivityRepositoryTest.class);

    @Resource
    private ActivityRepository activityRepository;

    private final static Long id = 24L;
    private PromotionActivity promotionActivity = new PromotionActivity();

    @Before
    public void initActivity(){
        promotionActivity.setActivityName("购物节");
        promotionActivity.setActivityDesc("限时五天");
        promotionActivity.setCouponId(9L);
        promotionActivity.setId(id);
        promotionActivity.setStatus(1);
    }

    /**
     * 通过优惠券模板id获得促销活动列表
     */
    @Test
    public void testGetActivityListByCouponId(){
        try {
            List<PromotionActivity> activities = activityRepository.getActivityListByCouponId(1L);
            if(!CollectionUtils.isEmpty(activities)){
                //couponId为1的测试数据有2条
                Assert.assertEquals(activities.size(),2L);
                for(PromotionActivity promotionActivity:activities){
                    Assert.assertNotNull(promotionActivity.getActivityName());
                    Assert.assertNotNull(promotionActivity.getStatus());
                }
            }
        } catch (Exception e) {
            log.error("校验返回的促销活动列表失败",e);
        }

    }

    /**
     * 新增一个促销活动
     */
    @Test
    public void testInsertActivity(){
        try {
            boolean result = activityRepository.insert(promotionActivity);
            Assert.assertTrue(result);
        } catch (Exception e) {
            log.error("校验新增促销活动失败",e);
        }
    }

    /**
     * 通过促销活动id获得促销活动实体
     */
    @Test
    public void testGetActivityById(){
        try {
            PromotionActivity activity = activityRepository.getActivityById(promotionActivity.getId());
            if(activity!=null){
                Assert.assertNotNull(activity.getCouponId());
                Assert.assertNotNull(activity.getActivityName());
                Assert.assertNotNull(activity.getStatus());
            }
        } catch (Exception e) {
            log.error("校验查询记录失败",e);
        }
    }

    /**
     * 更新促销活动记录
     */
    @Test
    public void testUpdateActivity(){
        promotionActivity.setEffectDays(5);
        try {
            boolean result = activityRepository.updateActivity(promotionActivity);
            Assert.assertTrue(result);
        } catch (Exception e) {
            log.error("校验更新记录失败",e);
        }
    }

    /**
     * 删除促销活动测试数据
     */
    @Test
    public void testDeleteActivity(){
        try {
            boolean result = activityRepository.deleteById(promotionActivity.getId());
            Assert.assertTrue(result);
        } catch (Exception e) {
            log.error("校验删除记录失败",e);
        }
    }

    /**
     * 通过优惠券模板列表获得促销活动id列表
     */
    @Test
    public void testGetActivityIdListByCouponIdList(){
        List<Long> couponIdList = new ArrayList<>();
        couponIdList.add(1L);
        couponIdList.add(2L);
        couponIdList.add(3L);
        List<Long> list = null;
        try {
            list = activityRepository.getActivityIdListByCouponIdList(couponIdList);
            if(!CollectionUtils.isEmpty(list)){
                Assert.assertEquals(list.size(),5L);
            }
        } catch (Exception e) {
            log.error("校验查询记录失败",e);
        }
    }
}

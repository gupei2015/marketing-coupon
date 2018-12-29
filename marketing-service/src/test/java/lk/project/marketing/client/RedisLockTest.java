package lk.project.marketing.client;

import lk.project.marketing.base.client.RedisLock;
import lk.project.marketing.base.entity.CouponSummary;
import lk.project.marketing.init.MarketingServiceApplication;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MarketingServiceApplication.class)
public class RedisLockTest {

    @Resource
    private RedisLock redisLock;


    /**
     *  测试并发领券／抢券场景下，使用redis分布锁累计领券总数
     */
    @Test
    public void testCountReceive() {


        CouponSummary couponSummary = new CouponSummary();
        couponSummary.setReceivedQuantity(0l);

        IntStream.range(0,50).parallel()
                .forEach( o->countReceiveQuantity(couponSummary) );
        System.out.println("Count Received quantities :" + couponSummary.getReceivedQuantity());
        Assert.assertNotEquals(50l, couponSummary.getReceivedQuantity().longValue());

        couponSummary.setReceivedQuantity(0l);
        IntStream.range(0,50).parallel()
                .forEach( o->countReceiveQuantityWithLock("test",1000L, couponSummary) );
        System.out.println("Count Received quantities with lock :" + couponSummary.getReceivedQuantity());
        Assert.assertEquals(50l, couponSummary.getReceivedQuantity().longValue());
    }

    private void countReceiveQuantityWithLock(String lockName, Long acquireTimeout, CouponSummary couponSummary)  {

        String lockIdentify = redisLock.lock(lockName,acquireTimeout);
        if (StringUtils.isNotEmpty(lockIdentify)){
            countReceiveQuantity(couponSummary);
            redisLock.releaseLock(lockName, lockIdentify);
        }
        else{
            System.out.println("get lock failed.");
        }

    }

    private void countReceiveQuantity(CouponSummary couponSummary){

        couponSummary.setReceivedQuantity(couponSummary.getReceivedQuantity()+1);
        try {
            Thread.sleep(10l);
        } catch (InterruptedException e) {
            System.out.println("count thread was interrupted");
        }

    }

}

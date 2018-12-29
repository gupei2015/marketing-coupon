package lk.project.marketing.service.impl;

import lk.project.marketing.base.bo.*;
import lk.project.marketing.init.MarketingServiceApplication;
import lk.project.marketing.service.CouponConsumeService;
import lk.project.marketing.service.CouponReceiveService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by alexlu on 2018/11/8.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MarketingServiceApplication.class)
//@Transactional
//@Rollback(true)
public class CouponConsumeServiceImplTest {
    private final static Logger log = LoggerFactory.getLogger(CouponConsumeServiceImplTest.class);

    @Resource
    CouponConsumeService couponConsumerService;

    @Resource
    CouponReceiveService couponReceiveService;

    /**
     * 测试校验当前用户下单和商品/服务优惠券是否可用
     */
    @Test
    public void testValidateCouponAvailable() {
        MemberBo memberBo = new MemberBo();
        memberBo.setUserId("1");
        memberBo.setUserCode("LK-User01");
        memberBo.setAge(21);
        memberBo.setGender(0);  //0-男,1-女
        memberBo.setLevel(2);
        memberBo.setPhoneNumber("13915862136");
        memberBo.setExpValue(25000L);
        memberBo.setUserName("Test1");

        try {
            Map<CouponReduceInfoBo, Map<Long, BigDecimal>> couponReduceAmountMap = new HashMap(16);
            Assert.assertEquals(true, couponConsumerService.validateCouponAvailable(memberBo, buildOrderDummyData(),
                    false, couponReduceAmountMap));
        } catch (Exception e) {
            log.error("校验当前用户下单和商品/服务优惠券是否可用失败", e);
        }
    }

    /**
     * 测试用券相关操作
     */
    @Test
    public void testConsumeCoupon(){
        List<CouponConsumeBo> resultList;

        MemberBo memberBo = new MemberBo();
        memberBo.setUserId("1");
        memberBo.setUserCode("LK-User01");
        memberBo.setAge(21);
        memberBo.setGender(0);  //0-男,1-女
        memberBo.setLevel(2);

        OrderBo orderBo = buildOrderDummyData();
        try{
            resultList = couponConsumerService.consumeCoupon(memberBo, orderBo);

            if(!CollectionUtils.isEmpty(resultList)) {
                Assert.assertEquals(8, resultList.size());  //订单+商品测试优惠券共计测试数据8张

                //校验返回的用劵结果集合
                for (CouponConsumeBo couponConsume : resultList){
                    for (CouponConsumeDetailBo detail : couponConsume.getCouponConsumeDetailList()) {
                        //校验是否有为空的用劵数量
                        Assert.assertNotEquals(0L, detail.getConsumeQuantity().longValue());

                        //校验是否将发券明细记录ID集合传入了用劵明细记录
                        Assert.assertNotNull(detail.getCouponReceiveDetailIds());

                        //校验是否和发券明细记录ID集合记录条数对应正确
    //                  Assert.assertEquals(detail.getCouponReceiveDetailIds(), couponReceiveService.getDetailCountByReceiveId(detail.getCouponReceiveId()));
                    }
                }
            }else {
                Assert.fail("用劵返回结果为空!");
            }
        }catch (Exception e){
            log.error("测试用劵操作失败!", e);
            Assert.fail("测试用劵操作发生异常");
        }
    }

    /**
     * 组装订单模拟请求数据
     */
    public OrderBo buildOrderDummyData(){
        OrderBo orderBo = new OrderBo();
        orderBo.setOrderId("101");
        orderBo.setOrderNo("LKD001");
        orderBo.setOrderDate(new Date());
        //orderBo.setReserveTime(new Time("15:01:00"));
        orderBo.setTotalOrderAmount(new BigDecimal(260.12).setScale(3, BigDecimal.ROUND_HALF_DOWN));
        orderBo.setPayOrderAmount(new BigDecimal(240.11).setScale(3, BigDecimal.ROUND_HALF_DOWN));
//        orderBo.setShopId("LKSP01");

        List<UseCouponBo> orderUseCouponList = new ArrayList<>();
//        orderUseCouponList.add(new UseCouponBo(1L, 3L));
//        orderUseCouponList.add(new UseCouponBo(2L, 5L));

//        orderUseCouponList.add(new UseCouponBo(2L, 2L));
        orderBo.setUseCoupons(orderUseCouponList);

        List<OrderItemBo> itemBoList = new ArrayList<>();

        //商品1
        OrderItemBo orderItemBo = new OrderItemBo();
        orderItemBo.setOrderItemId("11");
        orderItemBo.setGoodsQuantity(15L);
        orderItemBo.setSkuId("LKSKU01");
        orderItemBo.setSkuCategory("LKSKCAT01");
        orderItemBo.setTotalGoodsAmount(new BigDecimal(221.69).setScale(3, BigDecimal.ROUND_HALF_DOWN));
        orderItemBo.setPayGoodsAmount(new BigDecimal(210.12).setScale(3, BigDecimal.ROUND_HALF_DOWN));

        List<UseCouponBo> orderItemUseCouponList = new ArrayList<>();
//        orderItemUseCouponList.add(new UseCouponBo(1L, 2L));
//        orderItemUseCouponList.add(new UseCouponBo(4L, 4L));
        orderItemBo.setUseCoupons(orderItemUseCouponList);
        itemBoList.add(orderItemBo);

        //商品2
        orderItemBo.setOrderItemId("12");
        orderItemBo.setGoodsQuantity(7L);
        orderItemBo.setSkuId("LKSKU02");
        orderItemBo.setSkuCategory("LKSKCAT02");
        orderItemBo.setTotalGoodsAmount(new BigDecimal(129.15).setScale(3, BigDecimal.ROUND_HALF_DOWN));
        orderItemBo.setPayGoodsAmount(new BigDecimal(122.08).setScale(3, BigDecimal.ROUND_HALF_DOWN));

        orderItemUseCouponList = new ArrayList<>();
//        orderItemUseCouponList.add(new UseCouponBo(3L, 3L));
//        orderItemUseCouponList.add(new UseCouponBo(2L, 4L));
        orderItemBo.setUseCoupons(orderItemUseCouponList);
        itemBoList.add(orderItemBo);

        //商品3
        orderItemBo.setOrderItemId("13");
        orderItemBo.setGoodsQuantity(10L);
        orderItemBo.setSkuId("LKSKU03");
        orderItemBo.setSkuCategory("LKSKCAT03");
        orderItemBo.setTotalGoodsAmount(new BigDecimal(56.28).setScale(3, BigDecimal.ROUND_HALF_DOWN));
        orderItemBo.setPayGoodsAmount(new BigDecimal(51.16).setScale(3, BigDecimal.ROUND_HALF_DOWN));

        orderItemUseCouponList = new ArrayList<>();
//        orderItemUseCouponList.add(new UseCouponBo(1L, 2L));
//        orderItemUseCouponList.add(new UseCouponBo(3L, 4L));
        orderItemBo.setUseCoupons(orderItemUseCouponList);
        itemBoList.add(orderItemBo);
        orderBo.setOrderItemList(itemBoList);

        return orderBo;
    }
}

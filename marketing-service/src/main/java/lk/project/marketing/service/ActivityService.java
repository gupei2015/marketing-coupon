package lk.project.marketing.service;

import lk.project.marketing.base.entity.PromotionActivity;

import java.util.Date;

/**
 * Created by gupei on 2018/9/12.
 */
public interface ActivityService {

    /**
     * 当前是否活动发券有效期间校验
     * @param activePromotion
     * @return
     */
    static boolean verifyActivityPeriod(PromotionActivity activePromotion){

        Date currentDate = new Date();
        Date startDate = activePromotion.getActivityStart();
        Date endDate = activePromotion.getActivityEnd();

        if (startDate!=null&&startDate.after(currentDate)) return false;
        if (endDate!=null&&endDate.before(currentDate)) return false;
        return true;

    }

}

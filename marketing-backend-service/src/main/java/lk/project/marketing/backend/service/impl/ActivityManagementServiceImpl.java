package lk.project.marketing.backend.service.impl;


import lk.project.marketing.backend.repository.ActivityManagementRepository;
import lk.project.marketing.backend.service.ActivityManagementService;
import lk.project.marketing.base.bo.ActivityReqBo;
import lk.project.marketing.base.entity.PromotionActivity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by gupei on 2018/09/11.
 */
@Service
public class ActivityManagementServiceImpl implements ActivityManagementService {

    @Autowired
    ActivityManagementRepository activityManagementRepository;

    /**
     * 新增或更新促销活动
     * @param activityReqBo
     * @return
     */
    @Override
    public Boolean saveActivity(ActivityReqBo activityReqBo){
        PromotionActivity activity = new PromotionActivity();
        BeanUtils.copyProperties(activityReqBo,activity);
        return activityManagementRepository.insertOrUpdate(activity);
    }
}

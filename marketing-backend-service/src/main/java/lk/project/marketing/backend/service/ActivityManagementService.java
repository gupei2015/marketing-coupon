package lk.project.marketing.backend.service;

import lk.project.marketing.base.bo.ActivityReqBo;

/**
 * 促销活动相关后台管理服务
 * Created by luchao on 2018/12/25.
 */
public interface ActivityManagementService {

    /**
     * 新增或更新促销活动
     * @param activityReqBo
     * @return
     */
    Boolean saveActivity(ActivityReqBo activityReqBo);
}

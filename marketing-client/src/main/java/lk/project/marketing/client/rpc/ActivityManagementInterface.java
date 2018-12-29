package lk.project.marketing.client.rpc;

import lk.project.marketing.client.dto.ActivityReqDto;
import lk.project.marketing.client.vo.ResponseVO;

public interface ActivityManagementInterface {

    /**
     * 新增或更新促销活动
     * @param activityReqDto
     * @return
     */
    ResponseVO saveActivity(ActivityReqDto activityReqDto);
}

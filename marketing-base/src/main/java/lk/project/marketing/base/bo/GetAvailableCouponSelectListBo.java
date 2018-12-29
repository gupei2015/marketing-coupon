package lk.project.marketing.base.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by alexlu on 2018/11/13.
 */
@Data
public class GetAvailableCouponSelectListBo implements Serializable {
    /**
     * 可使用的优惠券列表
     * 按模板分组,可查看详情用户在每次活动领劵情况
     */
    private List<SelectCouponUseBo> selectCouponUseList;
}

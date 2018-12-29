package lk.project.marketing.base.bo;

import lk.project.marketing.base.constant.CommonConstants;
import lombok.Data;

/**
 * Created by dell on 2017/11/1.
 */
@Data
public class PagerBaseReqBo {
    /**
     * 是否进行分页(0: 不分页(默认),1：分页)
     */
    private Integer isPager = 0;

    /**
     * 分页页号(默认为第一页,索引为0)
     */
    private Integer pageNum = CommonConstants.DEFAULT_LIST_PAGE_INDEX;

    /**
     * 分页每页记录数(默认20)
     */
    private Integer pageSize = CommonConstants.DEFAULT_LIST_PAGE_SIZE;
}

package lk.project.marketing.base.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 基础分页结果响应类
 * Created by alexlu on 2018/6/22.
 */
@Data
public class PagerBaseRespBo<T> implements Serializable {
    /**
     * 总记录数
     */
    private Long totalCount;

    /**
     * 分页(单页)数据
     */
    private List<T> pageData;
}

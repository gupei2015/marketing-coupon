package lk.project.marketing.client.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by dell on 2017/11/1.
 */
@Data
public class PagerBaseRequestDto implements Serializable {
    /**
     * 是否进行分页(0: 不分页(默认),1：分页)
     */
    private Integer isPager = 0;

    /**
     * 分页页号(默认为第一页,索引为1)
     */
    private Integer pageNum = 1;

    /**
     * 分页每页记录数(默认20)
     */
    private Integer pageSize = 20;
}

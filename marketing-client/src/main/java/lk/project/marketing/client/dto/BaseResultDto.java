package lk.project.marketing.client.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by alexlu on 2018/11/15.
 */
@Data
public class BaseResultDto implements Serializable {
    /**
     * 操作结果(默认为true)
     */
    private Boolean operationResult = true;

    /**
     * 操作结果消息提示
     */
    private String resultMsg;
}

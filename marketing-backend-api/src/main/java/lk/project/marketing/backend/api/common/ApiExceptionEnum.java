package lk.project.marketing.backend.api.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 接口异常定义
 * Created by alexlu on 2017/10/13.
 */
public enum ApiExceptionEnum {

    ;

    private Integer code;
    private String apiMethodName;
    private String message;

    ApiExceptionEnum(Integer code, String apiMethodName, String message){
        this.code = code;
        this.apiMethodName = apiMethodName;
        this.message = message;
    }

    /**
     * @return code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * @return API method name
     */
    public String getApiMethodName() {
        return apiMethodName;
    }

    /**
     * @param apiMethodName the API method name to set
     */
    public void setApiMethodName(String apiMethodName) {
        this.apiMethodName = apiMethodName;
    }

    /**
     * @return message
     */
    public String getMessage() {
        return message;
    }


    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 根据方法名读取异常信息
     * @param apiActionUrl
     * @return
     */
    public static Map<Integer,String> getExpCodeMsgByApiUrl(String apiActionUrl) {
        Map<Integer,String> apiCodeMsg = new HashMap();
        for (ApiExceptionEnum apiException : ApiExceptionEnum.values()) {
            if (apiActionUrl.indexOf(apiException.getApiMethodName()) > 0) {
                apiCodeMsg.put(apiException.getCode(),apiException.getMessage());
                return apiCodeMsg;
            }
        }

        apiCodeMsg.put(40007,"系统异常!");
        return apiCodeMsg;
    }

    /**
     * 根据Code获取枚举对象
     * @param code
     * @return
     */
    public static ApiExceptionEnum getValueByCode(Integer code){
        for(ApiExceptionEnum apiExceptionEnum : ApiExceptionEnum.values()){
            if(apiExceptionEnum.getCode().equals(code)){
                return apiExceptionEnum;
            }
        }

        return null;
    }
}

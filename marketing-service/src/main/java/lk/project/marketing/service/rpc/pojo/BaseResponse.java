package lk.project.marketing.service.rpc.pojo;


import lk.project.marketing.base.enums.SystemErrorCodeEnum;
import lk.project.marketing.client.vo.ResponseVO;

import java.util.Map;

/**
 * 父级controller
 * Created by alexlu on 2017/1/24.
 */
public class BaseResponse {

    protected ResponseVO getSuccess(){
        return new ResponseVO(SystemErrorCodeEnum.SUCCESS.getCode(),"Succeed!");
    }

    protected ResponseVO getFromData(Object data){
        ResponseVO responseVO = getSuccess();
        responseVO.setData(data);
        return responseVO;
    }

    protected ResponseVO getFailure(){
        return new ResponseVO(SystemErrorCodeEnum.SYSTEM_ERROR.getCode(),"Failed!");
    }

    protected ResponseVO getFailure(String msg){
        return new ResponseVO(SystemErrorCodeEnum.SYSTEM_ERROR.getCode(),msg);
    }

    protected ResponseVO getFailure(Integer errorCode, String msg){
        return new ResponseVO(errorCode, msg);
    }

    protected ResponseVO getFailureWithMap(Integer errorCode, Map<String, String> errMsgMap){
        StringBuilder stb = new StringBuilder();
        for (String errMsg : errMsgMap.values()){
            stb.append(errMsg);
        }
        return new ResponseVO(errorCode, stb.toString());
    }

    protected ResponseVO getResponse(Object data){
        ResponseVO responseVO =  getSuccess();
        responseVO.setData(data);
        return responseVO;
    }
}

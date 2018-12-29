package lk.project.marketing.api.common;


import lk.project.marketing.client.vo.ResponseVO;

import java.util.Map;

/**
 * 父级controller
 * Created by alexlu on 2017/1/24.
 */
public class BaseController {

    protected ResponseVO getSuccess(){
        return new ResponseVO(20000,"Succeed!");
    }

    protected ResponseVO getFromData(Object data){
        ResponseVO responseVO = getSuccess();
        responseVO.setData(data);
        return responseVO;
    }

    protected ResponseVO getFailure(){
        return new ResponseVO(40000,"Failed!");
    }

    protected ResponseVO getFailure(String msg){
        return new ResponseVO(40000,msg);
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
        ResponseVO responseVO = getSuccess();
        responseVO.setData(data);
        return responseVO;
    }
}

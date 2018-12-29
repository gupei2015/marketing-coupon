package lk.project.marketing.backend.api.handler;

import lk.project.marketing.backend.api.common.ApiExceptionEnum;
import lk.project.marketing.backend.api.common.BaseController;
import lk.project.marketing.backend.api.common.CommonUtil;
import lk.project.marketing.client.exception.BusinessException;
import lk.project.marketing.client.vo.ResponseVO;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.Map;

/**
 * Created by alexlu
 * 2018/3/12.
 * 捕获全局异常
 */
@ControllerAdvice
public class GlobalExceptionHandler extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseVO jsonErrorHandler(HttpServletRequest request, Exception e) throws Exception {
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            BufferedReader reader = request.getReader();

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch ( Exception ignore ) {
        }

        String postData = sb.toString();
        String path = request.getRequestURI();
        int localPort = request.getLocalPort();

        logger.debug("[Request] IP:{}, Local Port:{}, Url:{}, Post Data:{}", CommonUtil.getIpAddr(request), localPort, path, postData);

        Map<Integer, String> apiCodeMsg = ApiExceptionEnum.getExpCodeMsgByApiUrl(request.getRequestURI());
        Map.Entry<Integer, String> entry = apiCodeMsg.entrySet().iterator().next();
        logger.error(String.format("%s异常:%s", entry.getValue(), ExceptionUtils.getStackTrace(e)));
//
//        if (e instanceof SystemErrorCodeEnum) {
//            BusinessException exp = (BusinessException) e;
//            if(exp.getCode() >= 0 &&  exp.getCode() <= 4){
//                logger.error("BusinessException occur:", e);
//                return getFailure(SystemErrorCodeEnum.SYSTEM_ERROR.getCode(), SystemErrorCodeEnum.SYSTEM_ERROR.getMessage());
//            }
//
//            return getFailure(exp.getCode(), exp.getMsg());
//        }else
//
        //Shiro exceptions
//        } else if (e instanceof UnauthorizedException) {
//            if (e.getMessage().indexOf(CommonConstants.SHIRO_FILTER_NO_AUTH_TEXT) != -1) {
//                String errMsg = String.format(BusinessErrorCodeEnum.SHIRO_FILTER_SUBJECT_ROLE_AUTH_NOT_ALLOWED.getMessage(), entry.getValue());
//                logger.error(String.format("%s,Exception:%s", errMsg, e.getStackTrace()));
//                return getFailure(BusinessErrorCodeEnum.SHIRO_FILTER_SUBJECT_ROLE_AUTH_NOT_ALLOWED.getCode(), errMsg);
//            }
//        } else if (e instanceof UnauthenticatedException) {
//            logger.error(String.format("%s,Exception:%s", BusinessErrorCodeEnum.SHIRO_FILTER_SUBJECT_INSTANCE_EMPTY.getMessage(), e.getStackTrace()));
//            return getFailure(BusinessErrorCodeEnum.SHIRO_FILTER_SUBJECT_INSTANCE_EMPTY.getCode(),
//                    BusinessErrorCodeEnum.SHIRO_FILTER_SUBJECT_INSTANCE_EMPTY.getMessage());
        if (e instanceof MethodArgumentNotValidException) {
            logger.error("参数异常");
            MethodArgumentNotValidException e1 = (MethodArgumentNotValidException) e;
            return getFailure(e1.getBindingResult().getFieldError().getDefaultMessage());
        } else if (e instanceof BusinessException) {
            return getFailure(((BusinessException) e).getErrorCode(), ((BusinessException) e).getResultMsg());
        }

        return getFailure(entry.getKey(), entry.getValue() + "发生异常,请稍后重试。");
    }
}

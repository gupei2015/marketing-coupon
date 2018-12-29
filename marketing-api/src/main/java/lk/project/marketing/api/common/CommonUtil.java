package lk.project.marketing.api.common;

/**
 * @Title: CommonUtil.java
 * @Package cn.enncloud.core
 * @Description: 通用util
 * @author alexlu
 * @date 2017年5月5日
 * @version V1.0
 */


import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * @ClassName: CommonUtil
 * @Description: 通用util
 * @author alexlu
 * @date 2017年5月5日
 *
 */

public class CommonUtil {
    /**
     *
     * @Title: getFieldValueByName
     * @Description: 根据属性名获取属性值
     * @param @param fieldName
     * @param @param o
     * @param @return    参数
     * @return Object    返回类型
     * @throws
     */
    public static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(o, new Object[]{});
            return value;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 生成n位验证码
     * @param codeNumsCount
     * @return
     */
    public static String generateVerifyCode(Integer codeNumsCount){
        String valCode = "";

        if(codeNumsCount > 0){
            for (Integer i = 0; i < codeNumsCount; i++){
                char c = (char)(randomInt(0, 9) + '0');
                valCode += String.valueOf(c);
            }
        }

        return  valCode;
    }

    /**
     * 生成随机数
     * @param from
     * @param to
     * @return
     */
    public static int randomInt(int from, int to){
        Random random= new Random();
        return from + random.nextInt(to - from);
    }

    /**
     * 获取IP地址
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 转换参数集合为Map对象
     * @param parameterMap
     * @param ins
     * @return
     */
    public static Map<String, String> convertParameter2Map (Map<String, String[]> parameterMap, InputStream ins) {
        Map<String, String> params = new TreeMap<String,String>();
        for (Iterator iter = parameterMap.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = parameterMap.get(name);
            String valueStr = "";
            for (int i = 0,len =  values.length; i < len; i++) {
                valueStr += (i == len - 1) ?  values[i] : values[i] + ",";
            }
            if (!valueStr.matches("\\w+")){
                try {
                    if(valueStr.equals(new String(valueStr.getBytes("iso8859-1"), "iso8859-1"))){
                        valueStr=new String(valueStr.getBytes("iso8859-1"), "UTF-8");
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            params.put(name, valueStr);
        }
        return params;
    }
}

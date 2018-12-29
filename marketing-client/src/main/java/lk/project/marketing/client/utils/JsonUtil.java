package lk.project.marketing.client.utils;

/**
  * @Title: JasonUtil.java
  * @Description: JsonUtil
  * @author alexlu
  * @date 2017年4月28日
  */

public class JsonUtil {
//    public static  <T> List<T> formatList(String jaosnStr,Class<T> clazz){
//	List<T> list = null;
//	try{
//	    if(StringUtils.isEmpty(jaosnStr)){
//		return null;
//	    }
//
//	    List<Object> lst = JsonFormatter.toObject(jaosnStr,List.class);
//	    if(lst.size() > 0){
//		list = lst.stream().map(j->formatObject(j, clazz)).collect(Collectors.toList());
//	    }
//	}catch(Exception e){
//	    e.printStackTrace();
//	    return null;
//	}
//	return list;
//    }

    public static <T> T formatObject(Object obj,Class<T> clazz){
	 T t = null;
	 try{
	     t = JsonFormatter.toObject(JsonFormatter.toJsonString(obj), clazz);
	 }catch(Exception e){
	     return null;
	 }
	 return t;
    }
}

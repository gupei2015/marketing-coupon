package lk.project.marketing.base.utils;

import com.google.gson.Gson;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pei Gu on 2018/9/25.
 */
public class PromotionRuleMatch {

    public static Object execExpression(String expression, String placeHolder, Object value) throws ScriptException {

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        engine.put(placeHolder, value);
        return engine.eval(expression);

    }

    public static Object execExpression(String expression, Map<String, Object> values) throws ScriptException {

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        values.entrySet().stream()
                .forEach(o -> engine.put(o.getKey(), o.getValue()));
        return engine.eval(expression);

    }

    public static boolean execJsonExpression(String expression, Map<String, Object> values) throws ScriptException {

        Gson gson = new Gson();
        Map<String, String> expressionMap = gson.fromJson(expression, Map.class);

        boolean result = true;
        for (Map.Entry<String, String> me : expressionMap.entrySet()) {
            result = (boolean) execExpression(me.getValue(), values);
            if (!result) break;
        }
        return result;

    }

    public static <T> Map<String, Object> buildFilterMap(T instance) throws IllegalAccessException {

        if (instance == null) return null;

        Map<String, Object> filterMap = new HashMap<>();
        Class filterClass = instance.getClass();

        for (Field field : filterClass.getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(instance);
            if (value != null) {
                if (value instanceof Date){
                    filterMap.put(field.getName(), ((Date)value).getTime());
                }
                else{
                    filterMap.put(field.getName(), value);
                }
            }
        }
        return filterMap;

    }

}

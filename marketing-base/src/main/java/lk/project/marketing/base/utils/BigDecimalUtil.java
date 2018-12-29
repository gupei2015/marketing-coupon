package lk.project.marketing.base.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by alexlu on 2018/11/29.
 */
public class BigDecimalUtil  {
    public static final int MONEY_POINT = 2;        // 货币保留两位小数

    /**
     * 格式化精度
     *
     * @param v
     * @param point
     *            小数位数
     * @return double
     */
    public static Double format(double v, int point) {
        BigDecimal b = new BigDecimal(v);
        return b.setScale(point, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     *
     * @param v
     * @param point
     * @return
     */
    public static Double formatRoundUp(double v, int point) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setRoundingMode(RoundingMode.HALF_UP);   //设置四舍五入
        nf.setMinimumFractionDigits(point);         //设置最小保留几位小数
        nf.setMaximumFractionDigits(point);         //设置最大保留几位小数
        return Double.valueOf(nf.format(v));
    }

    /**
     * 格式化金额,带千位符
     *
     * @param v
     * @return
     */
    public static String moneyFormat(Double v) {
        DecimalFormat formater = new DecimalFormat();
        formater.setMaximumFractionDigits(2);
        formater.setGroupingSize(3);
        formater.setRoundingMode(RoundingMode.FLOOR);
        return formater.format(v.doubleValue());
    }

    /**
     * 带小数的显示小数,不带小数的显示整数
     * @param d
     * @return
     */
    public static String doubleTrans(Double d) {
        if (Math.round(d) - d == 0) {
            return String.valueOf((long) d.doubleValue());
        }
        return String.valueOf(d);
    }

    /**
     * BigDecimal相加
     *
     * @param v1
     * @param v2
     * @return double
     */
    public static BigDecimal add(double v1, double v2) {
        BigDecimal n1 = new BigDecimal(Double.toString(v1));
        BigDecimal n2 = new BigDecimal(Double.toString(v2));
        return n1.add(n2);
    }

    /**
     * BigDecimal相减
     *
     * @param v1
     * @param v2
     * @return double
     */
    public static BigDecimal subtract(double v1, double v2) {
        BigDecimal n1 = new BigDecimal(Double.toString(v1));
        BigDecimal n2 = new BigDecimal(Double.toString(v2));
        return n1.subtract(n2);
    }

    /**
     * BigDecimal相乘
     *
     * @param v1
     * @param v2
     * @return double
     */
    public static BigDecimal multiply(double v1, double v2) {
        BigDecimal n1 = new BigDecimal(Double.toString(v1));
        BigDecimal n2 = new BigDecimal(Double.toString(v2));
        return n1.multiply(n2);
    }

    /**
     * BigDecimal相除
     *
     * @param v1
     * @param v2
     * @return double
     */
    public static BigDecimal divide(double v1, double v2, int scale) throws IllegalAccessException{
        if (scale < 0) {
            throw new IllegalAccessException("精确度不能小于0!");
        }

        BigDecimal n1 = new BigDecimal(Double.toString(v1));
        BigDecimal n2 = new BigDecimal(Double.toString(v2));
        return n1.divide(n2, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 精确除法
     *
     * @param scale 精度
     */
    public static double divide(String value1, String value2, int scale) throws IllegalAccessException {
        if (scale < 0) {
            throw new IllegalAccessException("精确度不能小于0");
        }

        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 比较大小 小于0：v1 < v2 大于0：v1 > v2 等于0：v1 = v2
     *
     * @param v1
     * @param v2
     * @return
     */
    public static int compare(double v1, double v2) {
        BigDecimal n1 = new BigDecimal(Double.toString(v1));
        BigDecimal n2 = new BigDecimal(Double.toString(v2));
        return n1.compareTo(n2);
    }

    /**
     * 四舍五入
     *
     * @param scale 小数点后保留几位
     */
    public static BigDecimal round(double v, int scale) throws IllegalAccessException {
        return divide(v, 1, scale);
    }

    /**
     * 四舍五入
     *
     * @param scale 小数点后保留几位
     */
    public static double round(String v, int scale) throws IllegalAccessException {
        return divide(v, "1", scale);
    }

    /**
     * 比较大小
     */
    public static boolean equalTo(BigDecimal b1, BigDecimal b2) {
        if (b1 == null || b2 == null) {
            return false;
        }
        return 0 == b1.compareTo(b2);
    }
}

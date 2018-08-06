package com.lanxinbase.system.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Random;

/**
 * Created by alan.luo on 2017/8/10.
 */
public class NumberUtils {

    private static final BigInteger LONG_MIN = BigInteger.valueOf(-9223372036854775808L);
    private static final BigInteger LONG_MAX = BigInteger.valueOf(9223372036854775807L);

    /**
     * 取随机数
     * @param min 最小范围
     * @param max 最大范围
     * @return
     */
    public static int getRandom(int min,int max){
        Random random = new Random();
        return random.nextInt(max)%(max-min+1) + min;
    }

    /**
     * 格式化数值
     * out(NumberUtils.formatNumber(NumberUtils.toDouble("12220.0032342344"),null));
     * out(NumberUtils.formatNumber(NumberUtils.toDouble("12220.1638942344"),"#0.0000"));
     * out(NumberUtils.formatNumber(NumberUtils.toDouble("12220.16932342344"),null));
     * @param number 需要格式化的数值
     * @param pattern 比如:#0.0000
     * @return string
     */
    public static String formatNumber(Number number,String pattern){
        if (isNaN(number.toString())){
            return "0.00";
        }

        if (pattern == null){
            pattern = "#0.00";
        }

        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(number);
    }

    /**
     * 格式化数值
     * @param number 字符串格式的数值
     * @param pattern 格式化参数：#0.0000
     * @return string
     */
    public static String formatNumber(String number,String pattern){
        return formatNumber(toDouble(number),pattern);
    }

    /**
     * 将数值转换为指定的数值类型
     * @param number 需要转换的数值
     * @param targetClass 数值类型：integer float double...
     * @param <T> 数值类型
     * @return 返回number
     * @throws IllegalArgumentException
     */
    public static <T extends Number> Number convertNumberToTargetClass(Number number, Class<T> targetClass) throws IllegalArgumentException {
        ObjectUtils.notNull(number, "Number must not be null");
        ObjectUtils.notNull(targetClass, "Target class must not be null");
        if (targetClass.isInstance(number)) {
            return number;
        } else {
            long value;
            if (Byte.class == targetClass) {
                value = checkedLongValue(number, targetClass);
                if (value < -128L || value > 127L) {
                    raiseOverflowException(number, targetClass);
                }

                return number.byteValue();
            } else if (Short.class == targetClass) {
                value = checkedLongValue(number, targetClass);
                if (value < -32768L || value > 32767L) {
                    raiseOverflowException(number, targetClass);
                }

                return number.shortValue();
            } else if (Integer.class != targetClass) {
                if (Long.class == targetClass) {
                    value = checkedLongValue(number, targetClass);
                    return value;
                } else if (BigInteger.class == targetClass) {
                    return number instanceof BigDecimal ? ((BigDecimal)number).toBigInteger() : BigInteger.valueOf(number.longValue());
                } else if (Float.class == targetClass) {
                    return number.floatValue();
                } else if (Double.class == targetClass) {
                    return number.doubleValue();
                } else if (BigDecimal.class == targetClass) {
                    return new BigDecimal(number.toString());
                } else {
                    throw new IllegalArgumentException("Could not convert number [" + number + "] of type [" + number.getClass().getName() + "] to unsupported target class [" + targetClass.getName() + "]");
                }
            } else {
                value = checkedLongValue(number, targetClass);
                if (value < -2147483648L || value > 2147483647L) {
                    raiseOverflowException(number, targetClass);
                }

                return number.intValue();
            }
        }
    }

    private static long checkedLongValue(Number number, Class<? extends Number> targetClass) {
        BigInteger bigInt = null;
        if (number instanceof BigInteger) {
            bigInt = (BigInteger)number;
        } else if (number instanceof BigDecimal) {
            bigInt = ((BigDecimal)number).toBigInteger();
        }
        if (bigInt != null && (bigInt.compareTo(LONG_MIN) < 0 || bigInt.compareTo(LONG_MAX) > 0)) {
            raiseOverflowException(number, targetClass);
        }
        return number.longValue();
    }

    private static void raiseOverflowException(Number number, Class<?> targetClass) {
        throw new IllegalArgumentException("Could not convert number [" + number + "] of type [" + number.getClass().getName() + "] to target class [" + targetClass.getName() + "]: overflow");
    }

    /**
     * 将数值文本转换为指定的数值类型
     * @param text 需要转换的数值文本
     * @param targetClass 数值类型：integer float double...
     * @param <T> 数值类型
     * @return 返回number
     * @throws IllegalArgumentException
     */
    public static <T extends Number> Number parseNumber(String text, Class<T> targetClass) {
        ObjectUtils.notNull(text, "Text must not be null");
        ObjectUtils.notNull(targetClass, "Target class must not be null");
        String trimmed = StringUtils.trimAllWhitespace(text);
        if (Byte.class == targetClass) {
            return isHexNumber(trimmed) ? Byte.decode(trimmed) : Byte.valueOf(trimmed);
        } else if (Short.class == targetClass) {
            return isHexNumber(trimmed) ? Short.decode(trimmed) : Short.valueOf(trimmed);
        } else if (Integer.class == targetClass) {
            return isHexNumber(trimmed) ? Integer.decode(trimmed) : Integer.valueOf(trimmed);
        } else if (Long.class == targetClass) {
            return isHexNumber(trimmed) ? Long.decode(trimmed) : Long.valueOf(trimmed);
        } else if (BigInteger.class == targetClass) {
            return isHexNumber(trimmed) ? decodeBigInteger(trimmed) : new BigInteger(trimmed);
        } else if (Float.class == targetClass) {
            return Float.valueOf(trimmed);
        } else if (Double.class == targetClass) {
            return Double.valueOf(trimmed);
        } else if (BigDecimal.class != targetClass && Number.class != targetClass) {
            throw new IllegalArgumentException("Cannot convert String [" + text + "] to target class [" + targetClass.getName() + "]");
        } else {
            return new BigDecimal(trimmed);
        }
    }

    /**
     * 字符串到整数
     * @param s 字符串
     * @return int
     */
    public static int toInt(String s){
        if (StringUtils.isEmptyTrim(s)){
            return 0;
        }
        Double d = Double.valueOf(s);
        return d.intValue();
    }

    /**
     * 字符串到浮点数
     * @param s 字符串
     * @return double
     */
    public static double toDouble(String s){
        if (StringUtils.isEmptyTrim(s)){
            return 0.0;
        }
        return Double.valueOf(s);
    }

    /**
     * 字符串到小数
     * @param s 字符串
     * @return float
     */
    public static float toFloat(String s){
        if (StringUtils.isEmptyTrim(s)){
            return 0.0f;
        }
        Double d = Double.valueOf(s);
        return d.floatValue();
    }

    /**
     * 字符串到字节
     * @param s 字符串
     * @return byte
     */
    public static byte toByte(String s){
        if (StringUtils.isEmptyTrim(s)){
            return 0;
        }
        Double d = Double.valueOf(s);
        return d.byteValue();
    }

    /**
     * 字符串到长整数
     * @param s 字符串
     * @return long
     */
    public static long toLong(String s){
        if (StringUtils.isEmptyTrim(s)){
            return 0L;
        }
        Double d = Double.valueOf(s);
        return d.longValue();
    }

    /**
     * 字符串到短整数
     * @param s 字符串
     * @return short
     */
    public static short toShort(String s){
        if (StringUtils.isEmptyTrim(s)){
            return 0;
        }
        Double d = Double.valueOf(s);
        return d.shortValue();
    }

    /**
     * 是否为NaN
     * @param s 字符串
     * @return boolean
     */
    public static boolean isNaN(String s){
        if (StringUtils.isEmptyTrim(s)){
            return true;
        }
        Double d = Double.valueOf(s);
        return d.isNaN();
    }

    private static boolean isHexNumber(String value) {
        int index = value.startsWith("-") ? 1 : 0;
        return value.startsWith("0x", index) || value.startsWith("0X", index) || value.startsWith("#", index);
    }

    private static BigInteger decodeBigInteger(String value) {
        int radix = 10;
        int index = 0;
        boolean negative = false;
        if (value.startsWith("-")) {
            negative = true;
            ++index;
        }

        if (!value.startsWith("0x", index) && !value.startsWith("0X", index)) {
            if (value.startsWith("#", index)) {
                ++index;
                radix = 16;
            } else if (value.startsWith("0", index) && value.length() > 1 + index) {
                ++index;
                radix = 8;
            }
        } else {
            index += 2;
            radix = 16;
        }

        BigInteger result = new BigInteger(value.substring(index), radix);
        return negative ? result.negate() : result;
    }


}

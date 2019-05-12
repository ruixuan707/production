package com.monco.common.bean;

import org.joda.time.DateTime;

import java.util.Date;
import java.util.Random;

/**
 * RandomUtils
 *
 * @author Monco
 * @version 1.0.0
 */
public class RandomUtils {

    /**
     * 生成物资随机码
     *
     * @return
     */
    public static String randomCode(Integer bit) {
        String sources = "0123456789"; // 加上一些字母，就可以生成pc站的验证码了
        Random rand = new Random();
        StringBuffer flag = new StringBuffer();
        for (int j = 0; j < bit; j++) {
            flag.append(sources.charAt(rand.nextInt(10)) + "");
        }
        return flag.toString();
    }

    /**
     * 生成商户号
     *
     * @return
     */
    public static String merchantNo() {
        StringBuilder stringBuilder = new StringBuilder("6501");
        DateTime dateTime = new DateTime(new Date());
        stringBuilder.append(dateTime.getYear() - 2000)
                .append(dateTime.getMonthOfYear() > 10 ? dateTime.getDayOfMonth() : "0" + dateTime.getMonthOfYear())
                .append(dateTime.getDayOfMonth() > 10 ? dateTime.getDayOfMonth() : "0" + dateTime.getDayOfMonth())
                .append(randomCode(ConstantUtils.NUM_6));
        return stringBuilder.toString();
    }

    /**
     * 生成订单号
     *
     * @return
     */
    public static String orderNo() {
        StringBuilder stringBuilder = new StringBuilder();
        Long timestamp = System.currentTimeMillis();
        stringBuilder.append(timestamp);
        return stringBuilder.toString();
    }

    /**
     * 生成流水号
     *
     * @return
     */
    public static String exchangeNo() {
        StringBuilder stringBuilder = new StringBuilder("6503");
        Long timestamp = System.currentTimeMillis();
        stringBuilder.append(timestamp);
        return stringBuilder.toString();
    }

}
package com.hu.health.member.utils;

import java.text.DecimalFormat;

public class CalBMIUtil {
    /**
     * BMI的公式=体重(kg)/身高(m)^2
     * @param weight
     * @param height
     * @return
     */
    public static double calBMI(Integer weight, Integer height){
        if(height==null||height==0||weight==null)return 0;
        return Double.parseDouble(MyDecimalFormat.getInstance().format(weight/Math.pow(height/100.0,2.0)));
    }
    static class  MyDecimalFormat{
        /**
         *  instance是静态的话，内部类也要是静态的
         *      因为静态成员在类加载的时候就运行，
         *      而非静态成员在new对象的时候才加载
         *      如果instance自动被加载，而其持有者MyDecimalFormat有不能自动加载，就矛盾了
          */
        private volatile static DecimalFormat instance=null;
        private static Object lock=new Object();
        public static DecimalFormat getInstance(){
            if(instance==null){
                synchronized (lock){
                    if(instance==null){
                        instance=new DecimalFormat("#.00");
                    }
                }
            }
            return instance;
        }
    }
}

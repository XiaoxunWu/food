package com.demo.util;
import java.awt.*;
import java.io.InputStream;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * 该方法为通用的工具类，放置一些共用的方法
 */
public class Util {
    public static Connection getConnection() throws Exception{
        //读取配置文件的4个基本信息
        InputStream is = Util.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties pros = new Properties();
        pros.load(is);
        String DBDRIVER = pros.getProperty("DBDRIVER");
        String DBURL = pros.getProperty("DBURL");
        String DBUSER = pros.getProperty("DBUSER");
        String DBPASSWORD = pros.getProperty("DBPASSWORD");
        //加载驱动
        Class.forName(DBDRIVER);
        //获取连接

        Connection connection=DriverManager.getConnection(DBURL,DBUSER,DBPASSWORD);
        return connection;
    }
    /**
     * 测试连接是否成功
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) {
        try {
            Connection conn = Util.getConnection();
            System.out.println("database connects successfully！！！");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("database connects failed！！！");
        }
    }
    /**
     * 获取系统当前时间并格式化为字符串
     *
     * @return
     */
    public static String getTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
    }

    /**
     *  时间并格式化为字符串
     *
     * @return
     */
    public static String getTime(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date.getTime());
    }

    public static Date transferString2Date(String s) {
        if (s == null) {
            return null;
        }
        Date date = new Date();
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date addDate(Date date,int i){
        if (date == null) {
            return null;
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,i); //把日期往后增加i天,整数  往后推,负数往前移动
        date=calendar.getTime(); //这个时间就是日期往后推i天的结果
        return date;
    }

    /**
     * 判断字符串是不是中文
     *
     * @param c
     * @return
     */
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS);
    }

    /**
     * 判断字符串是否是乱码
     *
     * @param strName
     * @return
     */
    public static boolean isMessyCode(String strName) {
        java.util.regex.Pattern p = java.util.regex.Pattern.compile("\\s*|\t*|\r*|\n*");
        java.util.regex.Matcher m = p.matcher(strName);
        String after = m.replaceAll("");
        String temp = after.replaceAll("\\p{P}", "");
        char[] ch = temp.trim().toCharArray();
        float chLength = 0;
        float count = 0;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!Character.isLetterOrDigit(c)) {
                if (!isChinese(c)) {
                    count = count + 1;
                }
                chLength++;
            }
        }
        return count / chLength > 0.4;
    }

    /**
     * 为防止页面传进来的内容因为编码不同等原因造成乱码，这里作统一的转换
     *
     * @param parameterName
     * @return
     */
    public static String decode(javax.servlet.http.HttpServletRequest request, String parameterName) {
        String str;
        if ((str = request.getParameter(parameterName)) == null) {
            return null;
        }
        try {
            if (isMessyCode(str)) {
                str = new String(str.getBytes("ISO-8859-1"), "UTF-8");
            }
            if (isMessyCode(str)) {
                str = new String(str.getBytes("GB2312"), "UTF-8");
            }
            if (isMessyCode(str)) {
                str = new String(str.getBytes("GBK"), "UTF-8");
            }
            if (isMessyCode(str)) {
                str = new String(str.getBytes("UTF-8"), "ISO-8859-1");
            }
            if (isMessyCode(str)) {
                str = new String(str.getBytes("GB2312"), "ISO-8859-1");
            }
            if (isMessyCode(str)) {
                str = new String(str.getBytes("GBK"), "ISO-8859-1");
            }
            if (isMessyCode(str)) {
                str = new String(str.getBytes("UTF-8"), "GB2312");
            }
            if (isMessyCode(str)) {
                str = new String(str.getBytes("ISO-8859-1"), "GB2312");
            }
            if (isMessyCode(str)) {
                str = new String(str.getBytes("GBK"), "GB2312");
            }
            if (isMessyCode(str)) {
                str = new String(str.getBytes("UTF-8"), "GBK");
            }
            if (isMessyCode(str)) {
                str = new String(str.getBytes("ISO-8859-1"), "GBK");
            }
            if (isMessyCode(str)) {
                str = new String(str.getBytes("GB2312"), "GBK");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(parameterName + "==" + str.trim());
        return str.trim();
    }
    // 返回一个随机颜色（Color对象）
    public static Color getRandomColor(int minColor, int maxColor) {
        Random random = new Random();
        // 保存minColor最大不会超过255
        if (minColor > 255)
            minColor = 255;
        //  保存minColor最大不会超过255
        if (maxColor > 255)
            maxColor = 255;
        //  获得红色的随机颜色值
        int red = minColor + random.nextInt(maxColor - minColor);
        //  获得绿色的随机颜色值
        int green = minColor + random.nextInt(maxColor - minColor);
        //  获得蓝色的随机颜色值
        int blue = minColor + random.nextInt(maxColor - minColor);
        return new Color(red, green, blue);
    }
}

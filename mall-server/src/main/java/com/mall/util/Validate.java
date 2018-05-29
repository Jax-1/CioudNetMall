package com.mall.util;




import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mall.message.ProcessResult;

/**
*
*
* @Classname: Validate.java
* @package: com.minmetals.basis.util.validation
* @project: CDV_MINMETAL_D~0~minmetals(2fbasis(2fjava_util~www.minmetals.com
* @Description: 数据合法性验证类
* @Company: Minmetals
* @Author: fantasylei
* @Version: 1.0
* @Create at: Nov 10, 2011 5:50:36 PM
* @Modification History:
* @Date Author Version Description
*       ---------------------------------------------------------- Nov 10, 2011
*       dev_leip 1.0 创建验证类
*/
public class Validate {

    /**
     * 非空验证
     *
     * @param value
     *            (需要验证的数据集)
     * @return验证全通过返回true,否则返回false
     */
    public static Boolean notNull(List<Object> value) {
        Boolean temp = true;
        for (Object object : value) {
            if (null == object || "".equals(object) || "null".equals(object)) {
                temp = false;
            }
        }
        return temp;
    }

    /**
     * 非空验证
     *
     * @param value
     *            Map<String, Object>
     * @return 如果值为空,则将其KEY返回,
     */
    public static String testNull(Map<String, Object> value) {
        Set<String> set = value.keySet();
        for (String key : set) {
            Object object = value.get(key);
            if (null == object || "".equals(object) || "null".equals(object)) {
                return key;
            }
        }
        return null;
    }
    
    /**
     * 非空验证
     *
     * @param value
     *            普通字符串
     * @return 为空则返回FALSE 否则返回TRUE
     */
    public static Boolean testNull(String value) {
        Boolean temp = null == value || "".equals(value) ? false : true;
        return temp;
    }

    /**
     * 非空验证
     *
     * @param value
     *            普通字符串
     * @return 为空则返回FALSE 否则返回TRUE
     */
    public static Boolean notNull(String value) {
        Boolean temp = null == value || "".equals(value) ? false : true;
        return temp;
    }
    
    public static Boolean checkNull(String value) {
        Boolean temp = null == value || "".equals(value)||"null".equalsIgnoreCase(value) ? false : true;
        return temp;
    }
    /**
     * 非空验证
     *
     * @param value
     *            对象
     * @return 为空则返回FALSE 否则返回TRUE
     */
    public static Boolean notNull(Object value) {
        Boolean temp = null == value ? false : true;
        return temp;
    }

    /**
     * 数字验证
     *
     * @param value
     * @return
     */
    public static Boolean isNum(Object value) {
        Boolean temp = false;
        if(value==null) return false;
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(value.toString());
        temp = matcher.matches();
        return temp;
    }

    /**
     * 长度验证
     *
     * @param value
     * @return
     */
    public static Boolean testLength(Integer min, Integer max, Object value) {
        Boolean temp = false;
        Pattern pattern = Pattern.compile(".{" + min + "," + max + "}");
        Matcher matcher = pattern.matcher(value.toString());
        temp = matcher.matches();
        return temp;
    }
    

    /**
     * 判断是否日期格式YYYY-MM-DD
     * @param sDate
     * @return
     */
    public static boolean checkYear(String sYear) {
         return checkDate(sYear+"-01-01");
    }   /**
     * 判断是否日期格式YYYY-MM-DD
     * @param sDate
     * @return
     */
    public static boolean checkDate(String sDate) {   
         String datePattern1 = "\\d{4}-\\d{2}-\\d{2}";   
         String datePattern2 = "^((\\d{2}(([02468][048])|([13579][26]))"  
         + "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"  
         + "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"  
         + "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?("  
         + "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"  
         + "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";   
         if ((sDate != null)) {   
         Pattern pattern = Pattern.compile(datePattern1);   
         Matcher match = pattern.matcher(sDate);   
         if (match.matches()) {   
         pattern = Pattern.compile(datePattern2);   
         match = pattern.matcher(sDate);   
         return match.matches();   
         } else {   
         return false;   
         }   
         }   
         return false;   
         }   
    
/**
* 校验是否超过SAP字符长度    
* @param str
* @param sapLen
* @return
*/
   public static boolean checkStrSAPLen(String str,int sapLen){
    //SAP校验自然位数
       if (str==null )return true;
       return str.length() <=sapLen;
   }
   
/**
* 校验是否超过数据库允许长度
* @param str
* @param dbLen
* @return
*/
   public static boolean checkStrDBLen(String str,int dbLen ){
        if (str==null) return true;
        return getStringDBLen(str)<= dbLen;
   }

    /**
     * 获取字符串占用数据字符串长度
     * @param str
     * @return
     */
    private   static int getStringDBLen(String str){
        if (str==null) return 0;
        int byteLen=0;
        try {
            byteLen=str.getBytes("UTF-8").length;
        } catch ( UnsupportedEncodingException e) {
            // TODO: handle exception
            
        }
        return byteLen;
    }
    /**
     * 校验手机号
     * @param mp
     * @return
     */
    public static boolean checkMobile(String mp) {
        return mp.matches("^1[3-8]\\d{9}$");   
    }
    /**
     * 校验是否email
     * @param email
     * @return
     */
    public static boolean checkEmail(String email)
    {
          boolean tag = true;
            final String pattern1 = "^([a-zA-Z0-9]+[_|_|\\-|.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|_|\\-|.]?)*[a-zA-Z0-9]+.[a-zA-Z]{2,4}$";
            final Pattern pattern = Pattern.compile(pattern1);
            final Matcher mat = pattern.matcher(email);
            if (!mat.find())
            {
                tag = false;
            }
            return tag;
    }
    
//    //判断整型
    public static boolean checkInteger(String str) {
        if (str.matches("[0-9]")||str.matches("[-]+[\\d]+")||str.matches("[0-9]+[\\d]+")||str.matches("[\\d]+")){
           return true;    
        }else{
            return false;
        }
    }
    //判断小数，与判断整型的区别在与d后面的小数点（红色）
    public static boolean checkDouble(String str) {
           return str.matches("[-]+[\\d.]+")||str.matches("[\\d.]+");
    }
    
    public   static int getZhFontLen(String str){
        if (str==null) {
             return 0 ;
        }else{
            int length=str.length();
            int byteLen=0;
            try {
                byteLen=str.getBytes("UTF-8").length;
            } catch ( UnsupportedEncodingException e) {
                // TODO: handle exception
                
            }
            return (byteLen-length)/2;
        }
    }
    
    /**
     * 检验身份证
     * @param idCard
     * @return
     */
    public static ProcessResult checkIDCard(String idCard) {
    	ProcessResult msg = new ProcessResult();
        String[] valCodeArr = { "1", "0", "x","9", "8", "7", "6", "5", "4","3", "2" };
        String[] wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7","9", "10", "5", "8", "4", "2" };
        String ai = "";
        // 号码的长度 15位或18位
        if (idCard.length() != 15 && idCard.length() != 18) {
            msg.setResult("E");
            msg.setMsg("身份证号码长度应该为15位或18位!");
            return msg;
        }
        // 除最后一位都为数字
        if (idCard.length() == 18) {
            ai = idCard.substring(0, 17);
        } else if (idCard.length() == 15) {
            ai = idCard.substring(0, 6) + "19" + idCard.substring(6, 15);
        }
        if (isNumeric(ai) == false) {
            msg.setResult("E");
            msg.setMsg("除最后一位外，都应为数字!");
            return msg;
        }
        // 出生年月是否有效
        String strYear = ai.substring(6, 10);// 年份
        String strMonth = ai.substring(10, 12);// 月份
        String strDay = ai.substring(12, 14);// 月份
        if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
            msg.setResult("E");
            msg.setMsg("身份证生日无效！");
            return msg;
        }
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
                    || (gc.getTime().getTime() - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
                msg.setResult("E");
                msg.setMsg("身份证生日不在有效范围！");
                return msg;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            msg.setResult("E");
            msg.setMsg("身份证月份无效！");
            return msg;
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            msg.setResult("E");
            msg.setMsg("身份证日期无效！");
            return msg;
        }
        // 地区编码有效
        Hashtable h = getAreaCode();
        if (h.get(ai.substring(0, 2)) == null) {
            msg.setResult("E");
            msg.setMsg("身份证地区编码错误！");
            return msg;
        }
        // 判断最后一位的值
        int totalMulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            totalMulAiWi = totalMulAiWi+ Integer.parseInt(String.valueOf(ai.charAt(i)))* Integer.parseInt(wi[i]);
        }
        int modValue = totalMulAiWi % 11;
        String strVerifyCode = valCodeArr[modValue];
        ai = ai + strVerifyCode.toUpperCase();

        if (idCard.length() == 18) {
            if (ai.equals(idCard.toUpperCase()) == false) {
                msg.setResult("E");
                msg.setMsg("身份证无效，不是合法的身份证号码！");
                return msg;
            }
        } else {
            msg.setResult("S");
            msg.setMsg("身份证校验通过！");
            return msg;
        }
        msg.setResult("S");
        msg.setMsg("身份证校验通过！");
        return msg;
    }

    /**
     * 功能：判断字符串是否为数字
     *
     * @param str
     * @return
     */
    private static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 功能：判断字符串是否为日期格式
     *
     * @param str
     * @return
     */
    public static boolean isDate(String strDate) {
        Pattern pattern = Pattern
                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 功能：设置地区编码
     *
     * @return Hashtable 对象
     */
    @SuppressWarnings("unchecked")
    private static Hashtable getAreaCode() {
        Hashtable hashtable = new Hashtable();
        hashtable.put("11", "北京");
        hashtable.put("12", "天津");
        hashtable.put("13", "河北");
        hashtable.put("14", "山西");
        hashtable.put("15", "内蒙古");
        hashtable.put("21", "辽宁");
        hashtable.put("22", "吉林");
        hashtable.put("23", "黑龙江");
        hashtable.put("31", "上海");
        hashtable.put("32", "江苏");
        hashtable.put("33", "浙江");
        hashtable.put("34", "安徽");
        hashtable.put("35", "福建");
        hashtable.put("36", "江西");
        hashtable.put("37", "山东");
        hashtable.put("41", "河南");
        hashtable.put("42", "湖北");
        hashtable.put("43", "湖南");
        hashtable.put("44", "广东");
        hashtable.put("45", "广西");
        hashtable.put("46", "海南");
        hashtable.put("50", "重庆");
        hashtable.put("51", "四川");
        hashtable.put("52", "贵州");
        hashtable.put("53", "云南");
        hashtable.put("54", "西藏");
        hashtable.put("61", "陕西");
        hashtable.put("62", "甘肃");
        hashtable.put("63", "青海");
        hashtable.put("64", "宁夏");
        hashtable.put("65", "新疆");
        hashtable.put("71", "台湾");
        hashtable.put("81", "香港");
        hashtable.put("82", "澳门");
        hashtable.put("91", "国外");
        return hashtable;
    }
    
    /**
     *
     * @Description:校驗字符串只存在字母、數字
     * @param      :
     * @author     :LXD
     * @Create at  :Mar 11, 2016 2:28:30 PM
     * @Modified by:
     */
    public static boolean checkString(String str)
    {
          boolean tag = true;
            final String pattern1 = "^[A-Z,a-z,0-9]*$";
            final Pattern pattern = Pattern.compile(pattern1);
            final Matcher mat = pattern.matcher(str);
            if (!mat.find())
            {
                tag = false;
            }
            return tag;
    }
    
}


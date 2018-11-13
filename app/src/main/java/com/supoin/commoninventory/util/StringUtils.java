package com.supoin.commoninventory.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String Utils
 * 
 * @author zhangfan
 */
public class StringUtils {

    private StringUtils() {
        throw new AssertionError();
    }

    /**
     * is null or its length is 0 or it is made by space
     * 
     * <pre>
     * isBlank(null) = true;
     * isBlank(&quot;&quot;) = true;
     * isBlank(&quot;  &quot;) = true;
     * isBlank(&quot;a&quot;) = false;
     * isBlank(&quot;a &quot;) = false;
     * isBlank(&quot; a&quot;) = false;
     * isBlank(&quot;a b&quot;) = false;
     * </pre>
     * 
     * @param str
     * @return if string is null or its size is 0 or it is made by space, return true, else return false.
     */
    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    /**
     * is null or its length is 0
     * 
     * <pre>
     * isEmpty(null) = true;
     * isEmpty(&quot;&quot;) = true;
     * isEmpty(&quot;  &quot;) = false;
     * </pre>
     * 
     * @param str
     * @return if string is null or its size is 0, return true, else return false.
     */
    public static boolean isEmpty(CharSequence str) {
        return (str == null || str.length() == 0);
    }

//    /**
//     * compare two string
//     * 
//     * @param actual
//     * @param expected
//     * @return
//     * @see ObjectUtils#isEquals(Object, Object)
//     */
//    public static boolean isEquals(String actual, String expected) {
//        return ObjectUtils.isEquals(actual, expected);
//    }

    /**
     * null Object to empty string
     * 
     * <pre>
     * nullStrToEmpty(null) = &quot;&quot;;
     * nullStrToEmpty(&quot;&quot;) = &quot;&quot;;
     * nullStrToEmpty(&quot;aa&quot;) = &quot;aa&quot;;
     * </pre>
     * 
     * @param str
     * @return
     */
    public static String nullStrToEmpty(Object str) {
        return (str == null ? "" : (str instanceof String ? (String)str : str.toString()));
    }

    /**
     * capitalize first letter
     * 
     * <pre>
     * capitalizeFirstLetter(null)     =   null;
     * capitalizeFirstLetter("")       =   "";
     * capitalizeFirstLetter("2ab")    =   "2ab"
     * capitalizeFirstLetter("a")      =   "A"
     * capitalizeFirstLetter("ab")     =   "Ab"
     * capitalizeFirstLetter("Abc")    =   "Abc"
     * </pre>
     * 
     * @param str
     * @return
     */
    public static String capitalizeFirstLetter(String str) {
        if (isEmpty(str)) {
            return str;
        }

        char c = str.charAt(0);
        return (!Character.isLetter(c) || Character.isUpperCase(c)) ? str : new StringBuilder(str.length())
                .append(Character.toUpperCase(c)).append(str.substring(1)).toString();
    }

    /**
     * encoded in utf-8
     * 
     * <pre>
     * utf8Encode(null)        =   null
     * utf8Encode("")          =   "";
     * utf8Encode("aa")        =   "aa";
     * utf8Encode("啊啊啊啊")   = "%E5%95%8A%E5%95%8A%E5%95%8A%E5%95%8A";
     * </pre>
     * 
     * @param str
     * @return
     * @throws java.io.UnsupportedEncodingException if an error occurs
     */
    public static String utf8Encode(String str) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("UnsupportedEncodingException occurred. ", e);
            }
        }
        return str;
    }

    /**
     * encoded in utf-8, if exception, return defultReturn
     * 
     * @param str
     * @param defultReturn
     * @return
     */
    public static String utf8Encode(String str, String defultReturn) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return defultReturn;
            }
        }
        return str;
    }

    /**
     * get innerHtml from href
     * 
     * <pre>
     * getHrefInnerHtml(null)                                  = ""
     * getHrefInnerHtml("")                                    = ""
     * getHrefInnerHtml("mp3")                                 = "mp3";
     * getHrefInnerHtml("&lt;a innerHtml&lt;/a&gt;")                    = "&lt;a innerHtml&lt;/a&gt;";
     * getHrefInnerHtml("&lt;a&gt;innerHtml&lt;/a&gt;")                    = "innerHtml";
     * getHrefInnerHtml("&lt;a&lt;a&gt;innerHtml&lt;/a&gt;")                    = "innerHtml";
     * getHrefInnerHtml("&lt;a href="baidu.com"&gt;innerHtml&lt;/a&gt;")               = "innerHtml";
     * getHrefInnerHtml("&lt;a href="baidu.com" title="baidu"&gt;innerHtml&lt;/a&gt;") = "innerHtml";
     * getHrefInnerHtml("   &lt;a&gt;innerHtml&lt;/a&gt;  ")                           = "innerHtml";
     * getHrefInnerHtml("&lt;a&gt;innerHtml&lt;/a&gt;&lt;/a&gt;")                      = "innerHtml";
     * getHrefInnerHtml("jack&lt;a&gt;innerHtml&lt;/a&gt;&lt;/a&gt;")                  = "innerHtml";
     * getHrefInnerHtml("&lt;a&gt;innerHtml1&lt;/a&gt;&lt;a&gt;innerHtml2&lt;/a&gt;")        = "innerHtml2";
     * </pre>
     * 
     * @param href
     * @return <ul>
     *         <li>if href is null, return ""</li>
     *         <li>if not match regx, return source</li>
     *         <li>return the last string that match regx</li>
     *         </ul>
     */
    public static String getHrefInnerHtml(String href) {
        if (isEmpty(href)) {
            return "";
        }

        String hrefReg = ".*<[\\s]*a[\\s]*.*>(.+?)<[\\s]*/a[\\s]*>.*";
        Pattern hrefPattern = Pattern.compile(hrefReg, Pattern.CASE_INSENSITIVE);
        Matcher hrefMatcher = hrefPattern.matcher(href);
        if (hrefMatcher.matches()) {
            return hrefMatcher.group(1);
        }
        return href;
    }

/**
     * process special char in html
     * 
     * <pre>
     * htmlEscapeCharsToString(null) = null;
     * htmlEscapeCharsToString("") = "";
     * htmlEscapeCharsToString("mp3") = "mp3";
     * htmlEscapeCharsToString("mp3&lt;") = "mp3<";
     * htmlEscapeCharsToString("mp3&gt;") = "mp3\>";
     * htmlEscapeCharsToString("mp3&amp;mp4") = "mp3&mp4";
     * htmlEscapeCharsToString("mp3&quot;mp4") = "mp3\"mp4";
     * htmlEscapeCharsToString("mp3&lt;&gt;&amp;&quot;mp4") = "mp3\<\>&\"mp4";
     * </pre>
     * 
     * @param source
     * @return
     */
    public static String htmlEscapeCharsToString(String source) {
        return StringUtils.isEmpty(source) ? source : source.replaceAll("&lt;", "<").replaceAll("&gt;", ">")
                .replaceAll("&amp;", "&").replaceAll("&quot;", "\"");
    }

    /**
     * transform half width char to full width char
     * 
     * <pre>
     * fullWidthToHalfWidth(null) = null;
     * fullWidthToHalfWidth("") = "";
     * fullWidthToHalfWidth(new String(new char[] {12288})) = " ";
     * fullWidthToHalfWidth("！＂＃＄％＆) = "!\"#$%&";
     * </pre>
     * 
     * @param s
     * @return
     */
    public static String fullWidthToHalfWidth(String s) {
        if (isEmpty(s)) {
            return s;
        }

        char[] source = s.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (source[i] == 12288) {
                source[i] = ' ';
                // } else if (source[i] == 12290) {
                // source[i] = '.';
            } else if (source[i] >= 65281 && source[i] <= 65374) {
                source[i] = (char)(source[i] - 65248);
            } else {
                source[i] = source[i];
            }
        }
        return new String(source);
    }

    /**
     * transform full width char to half width char
     * 
     * <pre>
     * halfWidthToFullWidth(null) = null;
     * halfWidthToFullWidth("") = "";
     * halfWidthToFullWidth(" ") = new String(new char[] {12288});
     * halfWidthToFullWidth("!\"#$%&) = "！＂＃＄％＆";
     * </pre>
     * 
     * @param s
     * @return
     */
    public static String halfWidthToFullWidth(String s) {
        if (isEmpty(s)) {
            return s;
        }

        char[] source = s.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (source[i] == ' ') {
                source[i] = (char)12288;
                // } else if (source[i] == '.') {
                // source[i] = (char)12290;
            } else if (source[i] >= 33 && source[i] <= 126) {
                source[i] = (char)(source[i] + 65248);
            } else {
                source[i] = source[i];
            }
        }
        return new String(source);
    }
    
    public static boolean isNumeric(String str)
    {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    public static boolean isDecimalInteger(String str)
    {
        /*
         * ^\\d+$ 是判断位正整数的 ^\\d+\\.\\d+$ 判断是否位正小数 -\\d+$：判断是否位负整
		 ?
         */

        // 判断是否为正整数或正小数
        Pattern pattern = Pattern.compile("^\\d+$|^\\d+\\.\\d+$");
        return pattern.matcher(str).matches();
    }

    public static boolean isDate(String str, String dateFormat)
    {
        if (str != null)
        {
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            formatter.setLenient(false);
            try
            {
                formatter.format(formatter.parse(str));
            }
            catch (Exception e)
            {
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean isDate(String str)
    {
        String dateFormat = "yyyy-MM-dd";
        if (str != null)
        {
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            formatter.setLenient(false);
            try
            {
                return str.equals(formatter.format(formatter.parse(str)));

            }
            catch (Exception e)
            {
                return false;
            }
        }
        return false;
    }

    public static boolean isDateTime(String str)
    {
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        if (str != null)
        {
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            formatter.setLenient(false);
            try
            {
                return str.equals(formatter.format(formatter.parse(str)));
            }
            catch (Exception e)
            {
                return false;
            }
        }
        return false;
    }

    public static boolean isMonth(String str)
    {
        String dateFormat = "yyyy-MM";
        if (str != null)
        {
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            formatter.setLenient(false);
            try
            {
                return str.equals(formatter.format(formatter.parse(str)));
            }
            catch (Exception e)
            {
                return false;
            }

        }
        return false;
    }

    public static String[] getArraysNum(String[][] Phones) throws Exception
    {
        try
        {
            for (int i = 0; i <= Phones[i].length; i++)
            {
                String[] b = new String[Phones[i].length];
                b[i] = String.valueOf(Phones[i]);
                return b;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isYear(String str)
    {
        String dateFormat = "yyyy";
        if (str != null)
        {
            if (str.length() != 4)
            {
                return false;
            }
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            formatter.setLenient(false);
            try
            {
                return str.equals(formatter.format(formatter.parse(str)));
            }
            catch (Exception e)
            {
                return false;
            }
        }
        return false;
    }

    public static boolean isEmail(String email)
    {
        String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        return m.find();
    }

    public static boolean isPhone(String phone)
    {
        String[] regex = new String[] {
                "^0(10|2[0-9]{1}|[3-9][0-9]{2})[1-9][0-9]{6,7}(\\+[0-9]{1,6})?$",
                "^1(3|4|5|8{1})([0-9]{1})([0-9]{8})?$", "^[0-9\\-]{11,20}$",
                "^400(\\d){7,7}$", "^400(\\d){7,7}(\\+[0-9]{1,4})?$" };
        for (int i = 0; i < regex.length; i++)
        {
            Pattern p = Pattern.compile(regex[i]);
            Matcher m = p.matcher(phone);
            if (m.find())
            {
                return true;
            }
        }

        return false;
    }

    // +86的不能打
    public static boolean isPhone86(String phone)
    {
        String[] regex = new String[] { "^86?", };
        for (int i = 0; i < regex.length; i++)
        {
            Pattern p = Pattern.compile(regex[i]);
            Matcher m = p.matcher(phone);
            if (m.find())
            {
                return true;
            }
        }
        return false;
    }

    // 是否可以拨打回拨的号码
    public static boolean isCallbackPhone(String phone)
    {
        String[] regex = new String[] {
                "^0(10|2[0-9]{1}|[3-9][0-9]{2})[1-9][0-9]{6,7}(\\+[0-9]{1,6})?$",
                "^1(3|4|5|8{1})([0-9]{1})([0-9]{8})?$" };
        for (int i = 0; i < regex.length; i++)
        {
            Pattern p = Pattern.compile(regex[i]);
            Matcher m = p.matcher(phone);
            if (m.find())
            {
                return true;
            }
        }
        return false;
    }

    // /^13\d{9}$/gi手机号正则表达式
    public static boolean isMobilePhone(String phone)
    {
        String regex = "^1(3|4|5|8{1})([0-9]{1})([0-9]{8})?$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(phone);
        return m.find();
    }

    public static boolean isUsername(String username)
    {
        String regex = "^[a-zA-Z0-9_]{3,20}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(username);
        return m.find();
    }

    public static boolean isPassword(String password)
    {
        String regex = "^[a-zA-Z0-9]{6,20}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        return m.find();
    }

    // 判断是否是字母和数字组成
    public static boolean isNumOrLetter(String str)
    {
        String regex = "^[0-9a-zA-Z]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.find();
    }

    public static String subZeroAndDot(String s)
    {
        if (s.indexOf(".") > 0)
        {
            s = s.replaceAll("0+?$", "");// 去掉多余?
            s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
        }
        return s;
    }

    // 判断是否是汉字、字母、数字和下划线组成
    public static boolean isChineseOrNumOrLetter(String str)
    {
        String regex = "^[0-9a-zA-Z_\u4e00-\u9fa5]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.find();
    }

    // 去掉电话号码中的空格
    public static String replaceBlank(String txtPhoneDial)
    {
        String dest = "";
        if (txtPhoneDial != null)
        {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(txtPhoneDial);
            dest = m.replaceAll("");
        }
        return dest;
    }

    // 去掉电话中的空格和“-”
    public static String replaceAll(String txtPhoneDial)
    {
        String dest = "";
        if (txtPhoneDial != null)
        {
            dest = txtPhoneDial.replaceAll("-", "").replaceAll(" ", "");
        }
        return dest;
    }

    // 去掉电话号码中的非数字字符和+86
    public static String replaceUnPhoneNumber(String str)
    {
        if (str == null)
        {
            return "";
        }
        // 只允许数字
        String regEx = "(^[+]86)|([^0-9])";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static int getRandomNumber()
    {
        int Min = 100000;
        int Max = 10 * Min;
        int number = (int) (Min + Math.random() * (Max - Min - 1));
        return number;
    }

    public static void main(String[] args)
    {
        System.out.println("isUsername33:" + isUsername("dennis_19"));
        System.out.println("isPassword:" + isPassword("222dd22"));
        System.out.println("isPhone:" + isPhone("075533445566"));
        System.out.println("isPhone:" + isPhone("0755-33445566"));
        System.out.println("isPhone:" + isPhone("4007160988+1233"));
        System.out.println("isMonth:" + isMonth("2011-01"));
        System.out.println("isDateTime:" + isDateTime("2011-01-01 00:00:00"));
        System.out.println("isDate:" + isDate("2011-01-01"));
        System.out.println("isYear:" + isYear("2222"));

        System.out.println("isEmail:" + isEmail("dennis123allywll.com"));
        System.out.println("isDecimalInteger:" + isDecimalInteger("1.5d"));
        String regex = "<(\\w+)>(\\w+)</(\\w+)>";
        Pattern pattern = Pattern.compile(regex);

        // String
        // input="<name>Bill</name><salary>50000</salary><title>GM</title>";
        String input = 	"<body><id>5</id><name>Bill</name><salary>50000</salary><title>GM</title></body>";
        System.out.println(input);
        Matcher matcher = pattern.matcher(input);

        while (matcher.find())
        {
            System.out.println(matcher.group(1) + "," + matcher.group(2) + ","
                    + matcher.group(3));
        }
    }
}

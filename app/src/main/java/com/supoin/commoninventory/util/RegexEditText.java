package com.supoin.commoninventory.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

/**
 *
 * @ClassName: RegexEditText
 * @Description: 自定义Edittext——集成正则表达式验证
 * @author zhangfan
 * @date 2016-03-31 下午2:56:31
 *
 */
public class RegexEditText extends EditText {
    /**
     *
     * @ClassName: RegexType
     * @Description: 正则匹配类别
     * @author lhy
     * @date 2014-10-21 下午3:31:12
     *
     */
    public enum RegexType {
        /**
         * 数字验证
         */
        Number,
        /**
         * 邮箱验证
         */
        Email,
        /**
         * 手机号码验证
         */
        Phone,
        /**
         * 网址
         */
        Url,
        /**
         * QQ验证
         */
        QQ,
        /**
         * 中文验证
         */
        Chinese
    }

    private Context context;

    private static Map<RegexType, String> regexMap;
    static {
        regexMap = new HashMap<RegexType, String>();
        regexMap.put(RegexType.Number, "^\\d+$");
        regexMap.put(RegexType.Email, "^[a-z A-Z 0-9 _]+@[a-z A-Z 0-9 _]+(\\.[a-z A-Z 0-9 _]+)+(\\,[a-z A-Z 0-9 _]+@[a-z A-Z 0-9 _]+(\\.[a-z A-Z 0-9 _]+)+)*$");
        regexMap.put(RegexType.Phone, "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        regexMap.put(RegexType.Url, "[a-zA-z]+://[^\\s]*");
        regexMap.put(RegexType.QQ, "[1-9][0-9]{4,}");
        regexMap.put(RegexType.Chinese, "[\\u4e00-\\u9fa5]");
    }

    public RegexEditText(Context context) {
        super(context);
        initEditText(context);
    }

    public RegexEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initEditText(context);
    }

    public RegexEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initEditText(context);
    }

    /**
     *
     * @Title: initEditText
     * @Description: 初始化EditText数据
     * @param @param context 设定文件
     * @return void 返回类型
     * @throws
     */
    private void initEditText(Context context) {
        this.context = context;
    }

    /**
     *
     * @Title: checkBody
     * @Description: 验证内容是否匹配
     * @param @param regexType
     * @param @return 设定文件
     * @return boolean 返回类型
     * @throws
     */
    public boolean checkBody(RegexType regexType) {
        return checkBody(regexMap.get(regexType));
    }

    /**
     *
     * @Title: checkBody
     * @Description: 验证内容是否匹配
     * @param @param regex 正则表达式
     * @param @return 设定文件
     * @return boolean 返回类型
     * @throws
     */
    public boolean checkBody(String regex) {
        String body = this.getText().toString().trim();
        if (body == null || body.equals("")) {
            Log.e(this.getClass().getSimpleName(), "EditText getText() is null or \"\"");
            return false;
        }
        Pattern pat = Pattern.compile(regex);
        Matcher mat = pat.matcher(body);
        boolean rs = mat.find();
        return rs;
    }
}

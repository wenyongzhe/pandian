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
 * @Description: �Զ���Edittext��������������ʽ��֤
 * @author zhangfan
 * @date 2016-03-31 ����2:56:31
 *
 */
public class RegexEditText extends EditText {
    /**
     *
     * @ClassName: RegexType
     * @Description: ����ƥ�����
     * @author lhy
     * @date 2014-10-21 ����3:31:12
     *
     */
    public enum RegexType {
        /**
         * ������֤
         */
        Number,
        /**
         * ������֤
         */
        Email,
        /**
         * �ֻ�������֤
         */
        Phone,
        /**
         * ��ַ
         */
        Url,
        /**
         * QQ��֤
         */
        QQ,
        /**
         * ������֤
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
     * @Description: ��ʼ��EditText����
     * @param @param context �趨�ļ�
     * @return void ��������
     * @throws
     */
    private void initEditText(Context context) {
        this.context = context;
    }

    /**
     *
     * @Title: checkBody
     * @Description: ��֤�����Ƿ�ƥ��
     * @param @param regexType
     * @param @return �趨�ļ�
     * @return boolean ��������
     * @throws
     */
    public boolean checkBody(RegexType regexType) {
        return checkBody(regexMap.get(regexType));
    }

    /**
     *
     * @Title: checkBody
     * @Description: ��֤�����Ƿ�ƥ��
     * @param @param regex ������ʽ
     * @param @return �趨�ļ�
     * @return boolean ��������
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

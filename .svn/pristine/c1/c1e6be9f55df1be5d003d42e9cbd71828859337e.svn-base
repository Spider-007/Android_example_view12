package com.htmitech.edittext;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.htmitech.addressbook.R;

public class SuperEditText extends EditText implements TextWatcher {
    public boolean isMustInput;
    public static boolean isChecked;
    public boolean isQianfen = false;
    private String value;
    private int TabStyle = 0; //0 是普通 1 是互联网风格

    public void setTabStyle(int tabStyle) {
        TabStyle = tabStyle;
    }

    public SuperEditText(Context context) {
        super(context);
        this.addTextChangedListener(this);
        // TODO Auto-generated constructor stub
    }

    public SuperEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.addTextChangedListener(this);
        // TODO Auto-generated constructor stub
    }

    public SuperEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.addTextChangedListener(this);
        // TODO Auto-generated constructor stub
    }

    public void setText(String src) {

    }

    public void setText(String src, int number) {
        super.setText(src);
//        IsMustInput(true,src);
        setMustInput(src);
    }


    public void IsMustInput(boolean isMustInput, String value, int TabStyle) {
        this.value = value;
        this.isMustInput = isMustInput;

        setTextSrc = value;
        if (TabStyle == 0) {
            if (!isMustInput) {
                setBackgroundResource(R.drawable.edittext_select_back);
            } else {

//           if(setTextSrc.equals(""))
                setBackgroundResource(R.drawable.edittext_select_back_unfocused);
//           else
//                setBackgroundResource(R.drawable.edittext_select_back);
            }
        } else {
            IsMustInput(isMustInput, value);
        }
    }

    public void IsMustInput(boolean isMustInput, String value) {
        this.value = value;
        this.isMustInput = isMustInput;

        setTextSrc = value;
        setMustInput(setTextSrc);
    }

    public void setMustInput(String textSrc) {
        if (TabStyle == 1) {
//            setBackgroundColor(getResources().getColor(R.color.white));
//            if (isMustInput) {
//                if (TextUtils.isEmpty(textSrc)) {
//                    setBackgroundResource(R.drawable.form_input_bg_must);
//                } else {
//                    setBackgroundResource(R.drawable.form_input_bg_required);
//                }
//
//            } else {
////                setBackgroundColor(getResources().getColor(R.color.white));
//                if (TextUtils.isEmpty(textSrc)) {
            setBackgroundColor(getResources().getColor(R.color.form_bg_white));
//                } else {
//                    setBackgroundResource(R.drawable.form_input_bg_required);
//                }
//
//            }
        }
//        else {
//            if (isMustInput) {
//                if (TextUtils.isEmpty(textSrc)) {
//                    setBackgroundResource(R.drawable.from_input_native_bt);
//                } else {
//                    setBackgroundResource(R.drawable.from_input_native_bt_face);
//                }
//
//            } else {
////                setBackgroundColor(getResources().getColor(R.color.white));
//                if (TextUtils.isEmpty(textSrc)) {
//                    setBackgroundResource(R.drawable.from_input_native_not_bt);
//                } else {
//                    setBackgroundResource(R.drawable.from_input_native_bt_face);
//                }
//            }
//        }


    }


    @Override
    public void onTextChanged(CharSequence s, int start, int before,
                              int count) {
        setTextSrc = s.toString();

//
    }

    public String setTextSrc = "";

    public static String touzi_ed_values22 = "";

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }

    /**
     * 处理千分符
     *
     * @param str
     * @param edtext
     * @return
     */
    public String addComma(String str, EditText edtext) {

        touzi_ed_values22 = str.toString().trim().replaceAll(",", "");
        try {
            str = Integer.parseInt(touzi_ed_values22) + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean neg = false;
        if (str.startsWith("-")) {  //处理负数
            str = str.substring(1);
            neg = true;
        }
        String tail = null;
        if (str.indexOf('.') != -1) { //处理小数点
            tail = str.substring(str.indexOf('.'));
            str = str.substring(0, str.indexOf('.'));
        }
        StringBuilder sb = new StringBuilder(str);
        sb.reverse();
        for (int i = 3; i < sb.length(); i += 4) {
            sb.insert(i, ',');
        }
        sb.reverse();
        if (neg) {
            sb.insert(0, '-');
        }
        if (tail != null) {
            sb.append(tail);
        }
        return sb.toString();
    }

    @Override
    public void afterTextChanged(Editable s) {
        IsMustInput(isMustInput, s.toString());

        if (isQianfen && !touzi_ed_values22.equals(this.getText().toString().trim().replaceAll(",", ""))) {
            String str = addComma(this.getText().toString().trim().replaceAll(",", ""), this);
            super.setText(str);
            super.setSelection(str.length());
        }

    }

    /**
     * 设置输入类型 是否是整数还是小数 还有是否设置千分符等问题
     *
     * @param intputType
     */
    public void setInputType(int intputType, int maxLength) {

        int length = maxLength / 3;
        int yushu = maxLength % 3;
        if (yushu == 0) {
            length = length - 1;
        }

        maxLength = maxLength + length;
        switch (intputType) {
            case 200:
                super.setInputType(EditorInfo.TYPE_CLASS_PHONE);
                this.setKeyListener(DigitsKeyListener.getInstance("0123456789"));

                this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
                isQianfen = false;
                break;
            case 201:
                super.setInputType(EditorInfo.TYPE_CLASS_PHONE);
                this.setKeyListener(new DigitsKeyListener(false, true));
                isQianfen = false;
                break;
            case 202:
                super.setInputType(EditorInfo.TYPE_CLASS_PHONE);
                this.setKeyListener(DigitsKeyListener.getInstance("0123456789"));

                this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
                isQianfen = true;
                break;
            case 203:
                super.setInputType(EditorInfo.TYPE_CLASS_PHONE);
                this.setKeyListener(new DigitsKeyListener(false, true));
                isQianfen = true;
                break;
        }
    }

    public boolean isMustInput() {
        return isMustInput;
    }

    public void setIsMustInput(boolean isMustInput) {
        this.isMustInput = isMustInput;
    }
}

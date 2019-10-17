package htmitech.com.componentlibrary;

import android.content.Context;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/5/30.
 */

public class SuperTextView extends TextView {

    public boolean isQianfen = false;

    public static String touzi_ed_values22 = "";

    public SuperTextView(Context context) {
        super(context);
    }

    public SuperTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SuperTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public String setTexts(CharSequence charSequence,int intputType){
        setInputType(intputType);

        if (isQianfen && !touzi_ed_values22.equals(this.getText().toString().trim().replaceAll(",", ""))) {

            String str = addComma(charSequence.toString().trim().replaceAll(",", ""));

            return str;
        }else{

           return charSequence.toString();

        }

    }

    /**
     * 处理千分符
     *
     * @param str
     * @return
     */
    public String addComma(String str) {

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


    public void setInputType(int intputType) {

        switch (intputType) {
            case 200:
                super.setInputType(EditorInfo.TYPE_CLASS_PHONE);
                this.setKeyListener(DigitsKeyListener.getInstance("0123456789"));

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

                isQianfen = true;
                break;
            case 203:
                super.setInputType(EditorInfo.TYPE_CLASS_PHONE);
                this.setKeyListener(new DigitsKeyListener(false, true));
                isQianfen = true;
                break;
        }
    }

}

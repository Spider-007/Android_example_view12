package widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.basewidgetlibrary.R;

import Interface.DialogCancelListener;
import Interface.DialogConfirmListener;

public class MyMessageDialog extends BaseDialog implements OnClickListener {

    private DialogConfirmListener onConfirmListener;

    private DialogCancelListener onCancelListener;

    private TextView content;
    private ImageView icon;

    private Button btn_confirm, btn_cancel;

    private String contentStr, confirmBtnStr, cancelBtnStr;
    private BitmapDrawable drawableIcon;
    private int iconId = -1, contentStrId = -1, confirmBtnStrId = -1, cancelBtnStrId = -1;

    private SpannableStringBuilder contentStyle;

    /*********************************************************************/
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.messagedialog_btn_confirm) {
            if (onConfirmListener != null) {
                onConfirmListener.onConfirm(this);
            }

        } else if (i == R.id.messagedialog_btn_cancel) {
            if (onCancelListener != null) {
                onCancelListener.onCancel(this);
            }

        }
        dismiss();
    }

    /*********************************************************************/
    public void setViewText(BitmapDrawable drawable, String content) {
        if (drawable != null) {
            this.drawableIcon = drawable;
        }
        if (content != null) {
            this.contentStr = content;
        }
    }

    public void setViewText(int drawable, String content) {
        this.iconId = drawable;
        if (content != null) {
            this.contentStr = content;
        }
    }

    public void setViewText(int iconId, int contentStrId) {
        this.iconId = iconId;
        this.contentStrId = contentStrId;
    }

    public void setViewText(int iconId, SpannableStringBuilder contentStyle) {
        this.iconId = iconId;
        this.contentStyle = contentStyle;
    }

    public void setButtonText(String confirmBtnStr, String cancelBtnStr) {
        if (confirmBtnStr != null) {
            this.confirmBtnStr = confirmBtnStr;
        }
        if (cancelBtnStr != null) {
            this.cancelBtnStr = cancelBtnStr;
        }
    }

    public void setButtonText(int confirmBtnStrId, int cancelBtnStrId) {
        this.confirmBtnStrId = confirmBtnStrId;
        this.cancelBtnStrId = cancelBtnStrId;
    }
    
    public void init() {
        drawableIcon = null;
        contentStr = null;
        confirmBtnStr = null;
        cancelBtnStr = null;

        iconId = -1;
        contentStrId = -1;
        confirmBtnStrId = -1;
        cancelBtnStrId = -1;
    }
 
    /*********************************************************************/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_messagedialog);

        initView();

        setData();
    }

    private void initView() {
        icon = (ImageView) findViewById(R.id.messagedialog_imgicon);
        content = (TextView) findViewById(R.id.messagedialog_content);
        btn_confirm = (Button) findViewById(R.id.messagedialog_btn_confirm);
        btn_cancel = (Button) findViewById(R.id.messagedialog_btn_cancel);

        btn_confirm.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

        btn_confirm.setText("关闭");
        btn_cancel.setText(R.string.cancel);
        btn_cancel.setVisibility(View.GONE);
    }

    private void setData() {
        if (drawableIcon != null) {
            icon.setImageDrawable(drawableIcon);
        } else if (iconId != -1) {
            icon.setImageResource(iconId);
        }
        if (contentStr != null) {
            content.setText(contentStr);
        } else if (contentStrId != -1) {
            content.setText(contentStrId);
        } else if (contentStyle != null) {
            content.setText(contentStyle);
        }
        if (confirmBtnStr != null) {
            btn_confirm.setText(confirmBtnStr);
            btn_cancel.setVisibility(View.GONE);
        } else if (confirmBtnStrId != -1) {
            btn_confirm.setText(confirmBtnStrId);
        }
        if (cancelBtnStr != null) {
            btn_cancel.setText(cancelBtnStr);
        } else if (cancelBtnStrId != -1) {
            btn_cancel.setText(cancelBtnStrId);
        }
    }



    public MyMessageDialog(Context context) {
        super(context);
        init();
    }

    public MyMessageDialog(Context context, DialogConfirmListener onConfirmListener) {
        super(context);
        init();
        this.onConfirmListener = onConfirmListener;
    }

    public MyMessageDialog(Context context, int theme, DialogConfirmListener onConfirmListener,
                           DialogCancelListener onCancelListener) {
        super(context , theme);
        init();
        this.onConfirmListener = onConfirmListener;
        this.onCancelListener = onCancelListener;
    }

}

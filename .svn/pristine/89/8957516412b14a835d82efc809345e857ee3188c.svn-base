package com.htmitech.combobox;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.htmitech.emportal.R;
import com.htmitech.emportal.ui.detail.ScrollViewListener;
import com.htmitech.unit.DensityUtil;


/**
 * 下拉框控件 2016/7/21
 */
public class ComboBox extends LinearLayout {

    private final static String TAG = "ComboBox";
    private ListViewItemClickListener m_listener;

    private View m_view;
    private ListView m_listView;
    private PopupWindow m_popupwindow;
    private ListViewAdapter m_adapter_listview;
    private String[] m_data;
    private Context m_context;
    private TextView m_Button;
    private EditText m_EditText;
    private LinearLayout m_Linearlayout;
    private TextView comboTextView;
    LayoutParams m_params;
    private Boolean m_isInput;
    private String textColor = "000000";
    private String text = "";
    private float zoom = 0;
    boolean isbutton = false;
    boolean isOpen = false;
    int screenHeight = 0;
    int[] location;
    View m_views;
    View newView;
    private boolean isMustinput = false;
    private ScrollViewListener mScrollViewListener;

    public boolean isMustinput() {
        return isMustinput;
    }

    public void setIsMustinput(boolean isMustinput) {
        this.isMustinput = isMustinput;
    }

    public void setScrollViewListener(ScrollViewListener mScrollViewListener) {
        this.mScrollViewListener = mScrollViewListener;
    }

    public ComboBox(Context context) {
        super(context);
        m_context = context;
        init();

    }

    public ComboBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        m_context = context;
        init();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void init() {
        newView = LayoutInflater.from(m_context).inflate(R.layout.combobox_test,
                this, true);

        m_Linearlayout = (LinearLayout) newView
                .findViewById(R.id.comboLinearlayout);
        m_Button = (TextView) newView.findViewById(R.id.comboButton);
        m_EditText = (EditText) newView.findViewById(R.id.comboEditText);
        comboTextView = (TextView) newView.findViewById(R.id.comboTextView);
        m_adapter_listview = new ListViewAdapter(m_context);
        m_view = LayoutInflater.from(m_context).inflate(
                R.layout.combobox_listview, null);

        m_listView = (ListView) m_view.findViewById(R.id.id_listview);
        m_listView.setAdapter(m_adapter_listview);

        m_listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                m_popupwindow.dismiss();
                if (m_isInput) {
                    if(isMustinput){
                        m_EditText.setBackground(getResources().getDrawable(R.drawable.combobox_false_ismustinput));
                        comboTextView.setBackground(getResources().getDrawable(R.drawable.combobox_false_ismustinput));
                    }else {

                        m_EditText.setBackground(getResources().getDrawable(R.drawable.combobox_false));
                        comboTextView.setBackground(getResources().getDrawable(R.drawable.combobox_false));
                    }
                } else {
                    if(isMustinput){
                        m_Linearlayout.setBackground(getResources().getDrawable(R.drawable.combobox_false_ismustinput));
                        m_EditText.setBackgroundResource(R.color.form_bg_must);
                        comboTextView.setBackgroundResource(R.color.form_bg_must);
                    }else {
                        m_Linearlayout.setBackground(getResources().getDrawable(R.drawable.combobox_false));
                    }

                }
                m_EditText.setText(m_data[position]);
                comboTextView.setText(m_data[position]);

                if (m_listener != null) {
                    m_listener.onItemClick(position);
                }
            }
        });

        setListeners();
    }

    ///---------一些set方法-----------------------------------------------------------
    public void setData(String[] data) {
        if (null == data || data.length <= 0) {
            return;
        }

        m_data = data;
//        m_EditText.setText(data[0]);//设置commbox是否有默认值
    }

    public void isEnable(boolean isInput) {
        m_isInput = isInput;
        if(isInput){
            comboTextView.setVisibility(GONE);
            m_EditText.setVisibility(VISIBLE);
        }else{
            comboTextView.setVisibility(VISIBLE);
            m_EditText.setVisibility(GONE);
        }
//        m_EditText.setEnabled(m_isInput);
        if (m_isInput) {
            if(isMustinput){
                m_EditText.setBackgroundResource(R.drawable.combobox_false_ismustinput);
                comboTextView.setBackgroundResource(R.drawable.combobox_false_ismustinput);
            }else {

                m_EditText.setBackgroundResource(R.drawable.combobox_false);
                comboTextView.setBackgroundResource(R.drawable.combobox_false);
            }
        } else {
            if(isMustinput){

                m_Linearlayout.setBackgroundResource(R.drawable.combobox_false_ismustinput);
                m_EditText.setBackgroundResource(R.color.form_bg_must);
                comboTextView.setBackgroundResource(R.color.form_bg_must);
            } else{

                m_Linearlayout.setBackgroundResource(R.drawable.combobox_false);
            }


        }

    }

    public void isMustInput(boolean isMustinput) {
        this.isMustinput = isMustinput;
        if(isMustinput) {
            m_Linearlayout.setBackgroundResource(R.color.form_bg_must);
            m_EditText.setBackgroundResource(R.color.form_bg_must);
            comboTextView.setBackgroundResource(R.color.form_bg_must);
        }
    }

    public void setBackgroundResource(int resurce){
        m_EditText.setBackgroundResource(resurce);
        comboTextView.setBackgroundResource(resurce);
    }


    public void setTextColor(String textcolor) {
        this.textColor = textcolor;
        m_EditText.setTextColor(Color.parseColor(textColor));
        comboTextView.setTextColor(Color.parseColor(textColor));
    }

    public void setText(String text) {
        this.text = text;
        m_EditText.setText(text);
        comboTextView.setText(text);

    }

    public void setListViewOnClickListener(ListViewItemClickListener listener) {
        m_listener = listener;

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void closePopupWindow() {
        if (m_popupwindow != null) {
            m_popupwindow.dismiss();
            if (m_isInput) {
                if(isMustinput){
                    m_EditText.setBackground(getResources().getDrawable(R.drawable.combobox_false_ismustinput));
                    comboTextView.setBackground(getResources().getDrawable(R.drawable.combobox_false_ismustinput));
                }else{

                    m_EditText.setBackground(getResources().getDrawable(R.drawable.combobox_false));
                    comboTextView.setBackground(getResources().getDrawable(R.drawable.combobox_false));
                }
            } else {
                if(isMustinput){
                    m_Linearlayout.setBackground(getResources().getDrawable(R.drawable.combobox_false_ismustinput));
                }else {

                    m_Linearlayout.setBackground(getResources().getDrawable(R.drawable.combobox_false));
                }
            }
        }
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setListeners() {

        m_Linearlayout.setOnClickListener(new OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  m_views = v;
                                                  if (m_isInput) {
                                                      if (isMustinput) {
                                                          m_EditText.setBackground(getResources().getDrawable(R.drawable.combobox_true_ismustinput));
                                                          comboTextView.setBackground(getResources().getDrawable(R.drawable.combobox_true_ismustinput));
                                                      } else {

                                                          m_EditText.setBackground(getResources().getDrawable(R.drawable.combobox_true));
                                                          comboTextView.setBackground(getResources().getDrawable(R.drawable.combobox_true));
                                                      }
                                                  } else {
                                                      if (isMustinput) {

                                                          m_Linearlayout.setBackground(getResources().getDrawable(R.drawable.combobox_true_ismustinput));

                                                      } else {
                                                          m_Linearlayout.setBackground(getResources().getDrawable(R.drawable.combobox_true));
                                                      }
                                                  }

                                                  InputMethodManager imm = (InputMethodManager) m_context.getSystemService(Context.INPUT_METHOD_SERVICE);
                                                  imm.hideSoftInputFromWindow(m_EditText.getWindowToken(), 0);
                                                  WindowManager a = (WindowManager) m_context
                                                          .getSystemService(Context.WINDOW_SERVICE);
                                                  Display d = a.getDefaultDisplay(); // 获取屏幕宽、高用
                                                  screenHeight = d.getHeight(); // 屏幕高度
                                                  location = new int[2];
                                                  m_Linearlayout.getLocationOnScreen(location);

                                                  if (m_popupwindow == null) {


                                                      if (m_data != null && m_data.length >= 4) {

                                                          if (m_isInput) {
                                                              m_popupwindow = new PopupWindow(m_view, ComboBox.this
                                                                      .getWidth() - m_Button.getWidth(), DensityUtil.dip2px(m_context, 200));// LayoutParams.WRAP_CONTENT);
                                                          } else {
                                                              m_popupwindow = new PopupWindow(m_view, ComboBox.this
                                                                      .getWidth(), DensityUtil.dip2px(m_context, 200));// LayoutParams.WRAP_CONTENT);
                                                          }


                                                      } else {
                                                          if (m_isInput) {

                                                              m_popupwindow = new PopupWindow(m_view, ComboBox.this
                                                                      .getWidth() - m_Button.getWidth(),
                                                                      LayoutParams.WRAP_CONTENT);// LayoutParams.WRAP_CONTENT);
                                                          } else {
                                                              m_popupwindow = new PopupWindow(m_view, ComboBox.this
                                                                      .getWidth(),
                                                                      LayoutParams.WRAP_CONTENT);// LayoutParams.WRAP_CONTENT);
                                                          }

                                                      }

                                                      // 点击PopUpWindow外面的控件也可以使得PopUpWindow dimiss。
                                                      // 需要顺利让PopUpWindow dimiss；PopUpWindow的背景不能为空。
                                                      m_popupwindow.setBackgroundDrawable(new BitmapDrawable());

                                                      // 获得焦点，并且在调用setFocusable（true）方法后，可以通过Back(返回)菜单使PopUpWindow
                                                      // dimiss
                                                      m_popupwindow.setFocusable(true);
                                                      m_popupwindow.setOutsideTouchable(true);
                                                      m_popupwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

                                                          public void onDismiss() {
                                                              mScrollViewListener.onScrollChanged();
                                                          }
                                                      });


                                                      if (screenHeight - location[1] > ComboBox.this.getHeight() * 4) {
                                                          m_popupwindow.showAsDropDown(ComboBox.this, 0, 0);
                                                      } else {


                                                          m_popupwindow.showAtLocation(m_views, Gravity.NO_GRAVITY,
                                                                  location[0],
                                                                  location[1] - m_popupwindow.getHeight());


                                                      }


                                                  } else if (m_popupwindow.isShowing())

                                                  {
                                                      m_popupwindow.dismiss();
                                                      if (m_isInput) {
                                                          if (isMustinput) {
                                                              m_EditText.setBackground(getResources().getDrawable(R.drawable.combobox_false_ismustinput));
                                                              comboTextView.setBackground(getResources().getDrawable(R.drawable.combobox_false_ismustinput));
                                                          } else {
                                                              m_EditText.setBackground(getResources().getDrawable(R.drawable.combobox_false));
                                                              comboTextView.setBackground(getResources().getDrawable(R.drawable.combobox_false));
                                                          }
                                                      } else {
                                                          if (isMustinput) {
                                                              m_Linearlayout.setBackground(getResources().getDrawable(R.drawable.combobox_false_ismustinput));
                                                          } else {
                                                              m_Linearlayout.setBackground(getResources().getDrawable(R.drawable.combobox_false));
                                                          }
                                                      }
                                                  } else

                                                  {

                                                      if (m_isInput) {
                                                          if (isMustinput) {
                                                              m_EditText.setBackground(getResources().getDrawable(R.drawable.combobox_true_ismustinput));
                                                              comboTextView.setBackground(getResources().getDrawable(R.drawable.combobox_true_ismustinput));
                                                          } else {

                                                              m_EditText.setBackground(getResources().getDrawable(R.drawable.combobox_true));
                                                              comboTextView.setBackground(getResources().getDrawable(R.drawable.combobox_true));
                                                          }
                                                      } else {
                                                          if (isMustinput) {
                                                              m_Linearlayout.setBackground(getResources().getDrawable(R.drawable.combobox_true_ismustinput));
                                                          } else {
                                                              m_Linearlayout.setBackground(getResources().getDrawable(R.drawable.combobox_true));
                                                          }

                                                      }


                                                      if (screenHeight - location[1] > ComboBox.this.getHeight() * 4) {
                                                          m_popupwindow.showAsDropDown(ComboBox.this, 0, 0);
                                                      } else {


                                                          m_popupwindow.showAtLocation(m_views, Gravity.NO_GRAVITY,
                                                                  location[0],
                                                                  location[1] - m_popupwindow.getHeight());


                                                      }

                                                  }
                                              }

                                          }

        );

    }

    class ListViewAdapter extends BaseAdapter {
        private LayoutInflater m_inflate;

        public ListViewAdapter(Context context) {
            // TODO Auto-generated constructor stub
            m_inflate = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return m_data == null ? 0 : m_data.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            TextView textview = null;

            if (convertView == null) {
                convertView = m_inflate.inflate(R.layout.combobox_item, null);
                textview = (TextView) convertView.findViewById(R.id.id_txt);

                convertView.setTag(textview);
            } else {
                textview = (TextView) convertView.getTag();
            }
            if (zoom != 0) {
                textview.setTextSize(zoom);
            }
            textview.setText(m_data[position]);

            return convertView;
        }
    }

    public void setTextSize(float zoom) {
        this.zoom = zoom;
        m_EditText.setTextSize(zoom);
        comboTextView.setTextSize(zoom);
    }

    public String getText() {
        if(m_isInput){
            return m_EditText.getText().toString();
        }else{
            return comboTextView.getText().toString();
        }
    }

    public interface ListViewItemClickListener {
        void onItemClick(int position);
    }

}


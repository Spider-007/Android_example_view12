package com.htmitech.proxy.activity;

import android.app.TabActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.example.ztcustom.MainActivity;
import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.emportal.R;

/**
 * Created by Administrator on 2019/9/20 0020.
 */

public class AppcenterImageActivity extends BaseFragmentActivity{

    final int RIGHT = 0;
    final int LEFT = 1;
    private GestureDetector gestureDetector;
    private ImageView iv;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.platform_child_layout);

        String source = getIntent().getStringExtra("source");
        iv = (ImageView) findViewById(R.id.iv);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (TextUtils.equals(source, "app")) {
            iv.setBackgroundResource(R.drawable.help_application);
        }else if (TextUtils.equals(source, "work")) {
            iv.setBackgroundResource(R.drawable.help_circle);
        } else if(TextUtils.equals(source, "call")) {
            iv.setBackgroundResource(R.drawable.help_book);
        }
               /* gestureDetector = new GestureDetector(AppcenterImageActivity.this,onGestureListener);*/
    }

          /*  private GestureDetector.OnGestureListener onGestureListener =
                new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                        float velocityY) {
                    float x = e2.getX() - e1.getX();
                    float y = e2.getY() - e1.getY();

                    if (x > 0) {
                        doResult(RIGHT);
                    } else if (x < 0) {
                        doResult(LEFT);
                    }
                    return true;
                }
            };

            public boolean onTouchEvent(MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }

            public void doResult(int action) {

                switch (action) {
                case RIGHT:
                    System.out.println("go right");
                    break;

                case LEFT:
                    System.out.println("go left");
                    break;

                }
            }*/
}
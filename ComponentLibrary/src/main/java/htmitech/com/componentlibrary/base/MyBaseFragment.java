package htmitech.com.componentlibrary.base;

import android.graphics.Point;
import android.view.MotionEvent;
import android.widget.Toast;


public abstract class MyBaseFragment extends BaseFragment{
    private boolean isWaitingForOnMyResume = false;
    /** Fragment当前状态是否可见 */
    protected boolean isVisible;
    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (isWaitingForOnMyResume) {
            isWaitingForOnMyResume = false;
            onMyResume();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
        } else {
            isVisible = false;
        }
        if (isVisibleToUser) {
            if (isResumed()) {
                onMyResume();
            } else {
                isWaitingForOnMyResume = true;
            }

        } else {
            if (isResumed())
                onMyPause();
        }
    }

    /**
     * viewPager中界面每次可见调用；
     */
    public void onMyResume() {

    }

    /**
     * viewPager中界面每次不可见调用；
     */
    public void onMyPause() {

    }


}

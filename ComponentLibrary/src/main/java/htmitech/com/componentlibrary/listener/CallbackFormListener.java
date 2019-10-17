package htmitech.com.componentlibrary.listener;

import htmitech.com.componentlibrary.entity.EditField;

/**
 * Created by Think on 2017/8/25.
 */

public interface CallbackFormListener {

    void onFormClick(EditField edit);


    public class DefaultCallbackFormListener implements CallbackFormListener{

        @Override
        public void onFormClick(EditField edit) {

        }
    }

}

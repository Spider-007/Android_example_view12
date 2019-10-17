package htmitech.com.componentlibrary.listener;

import android.view.View;

/**
 * 统一处理将对应的Key做特殊处理转换Value值
 */
public interface IFindKeyByValueListener {

    public String findKeyByValueListener(View view,String key, String value);


    public class DefaultFindKeyByValueListener implements IFindKeyByValueListener {

        @Override
        public String findKeyByValueListener(View view,String key, String value) {
            return value;
        }
    }
}

package htmitech.com.componentlibrary.listener;

import android.view.View;

import java.util.List;

import htmitech.com.componentlibrary.entity.DocResultInfo;
import htmitech.com.componentlibrary.entity.EditField;

/**
 * Created by Administrator on 2018/5/24.
 */

public interface ISearch413 {
    public void getSearch(View view, int intputType, List<EditField> editFields, DocResultInfo mDocResultInfo, int TabStyle);

    public class DefaultISearch implements ISearch413 {

        @Override
        public void getSearch(View view, int intputType, List<EditField> editFields, DocResultInfo mDocResultInfo, int TabStyle) {

        }
    }
}

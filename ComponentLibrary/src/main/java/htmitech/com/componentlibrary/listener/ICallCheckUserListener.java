package htmitech.com.componentlibrary.listener;

import android.view.View;

import java.util.ArrayList;

import htmitech.com.componentlibrary.entity.AuthorInfo;

/**
 * Created by htrf-pc on 2018-3-19.
 */
public interface ICallCheckUserListener {

    enum IChooseWay {
        DEPARTMENTCHOOSE,//部门选择
        PEOPLECHOOSE,//人员选择
        FREECHOOSE;//自由选择
    }
    public enum Choose {

        SINGLE_CHOOSE,//单选
        MORE_CHOOSE;// 多选
    }

    public void getCheckUserListener(View view,IChooseWay mChooseWay,Choose mChoose);


    public class DefaultICallCheckUserListener implements ICallCheckUserListener{

        @Override
        public void getCheckUserListener(View view,IChooseWay mChooseWay,Choose mChoose) {
        }
    }
}

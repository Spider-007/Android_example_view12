package htmitech.com.componentlibrary.entity;


/**
 * Created by htrf-pc on 2017/8/31.
 */
public class Basic {

    public CheckForm checkForm;//提供最基本的title名字

    public boolean isCheck;//表示是否选中

    public Basic(){

    }

    public Basic(CheckForm checkForm,boolean isCheck){
        this.checkForm = checkForm;
        this.isCheck = isCheck;
    }
}

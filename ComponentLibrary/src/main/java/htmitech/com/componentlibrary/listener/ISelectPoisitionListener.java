package htmitech.com.componentlibrary.listener;

/**
 * 主要处理关于7001的一些回调
 */
public interface ISelectPoisitionListener {

    /**
     * 主要是回调关于主工程中需要用到的搜索类 如高德 如 腾讯 如 百度
     */
    public void startSchedulePoiSearchContext();

    public class DefaultISelectPoisitionListener implements ISelectPoisitionListener {


        @Override
        public void startSchedulePoiSearchContext() {

        }
    }
}

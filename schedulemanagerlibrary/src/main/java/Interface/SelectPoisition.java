package Interface;


import schedulebean.ScheduleCpId;

/**
 * Created by yanxin on 2017-9-25.
 */
public class SelectPoisition {
    public SelectPoisitionInterface mSelectPoisitionInterface;
    public static SelectPoisition mSelectPoisition;
    private ScheduleCpId mScheduleCpId;

    public ScheduleCpId getmScheduleCpId() {
        return mScheduleCpId;
    }

    public void setmScheduleCpId(ScheduleCpId mScheduleCpId) {
        this.mScheduleCpId = mScheduleCpId;
    }

    public static SelectPoisition getInstances(){
        if(null == mSelectPoisition){
            SelectPoisition selectPoisition = new SelectPoisition();
            mSelectPoisition = selectPoisition;
            return mSelectPoisition;
        }
        return mSelectPoisition;
    }


    public interface SelectPoisitionInterface {
        void OnLocationChange(ScheduleCpId mScheduleCpId);
    }

    public void setSelectPoisitionInterface(SelectPoisitionInterface mSelectPoisitionInterface) {
            this.mSelectPoisitionInterface = mSelectPoisitionInterface;
    }
    public SelectPoisitionInterface getSelectPoisitionInterface(){
        return this.mSelectPoisitionInterface;
    }
}



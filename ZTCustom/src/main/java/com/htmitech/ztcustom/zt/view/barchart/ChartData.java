
package com.htmitech.ztcustom.zt.view.barchart;
/**
 * Created by Idtk on 2016/6/6.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 图表数据基类
 */
public class ChartData extends BaseData implements IChartData{
    protected String name="";

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

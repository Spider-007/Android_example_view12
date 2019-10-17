package com.htmitech.myEnum;

/**
 *
 *
 * 根据配置来配置ENUM
 *
 */
public enum TransitEnum {

    PEOPLE_REQUEST_ID(ChooseWay.MORE_CHOOSE,"611"),//返回的是用户ID 可以获取选择方式 是多选

    PEOPLE_REQUEST_NAME(ChooseWay.MORE_CHOOSE,"612"),//返回的是用户Name 可以获取选择方式 是多选

    PEOPLE_REQUEST_LOGIN_NAME(ChooseWay.MORE_CHOOSE,"613"),//返回的是用户login 可以获取选择方式 是多选

    DEPARTMENT_REQUEST_NAME(ChooseWay.MORE_CHOOSE,"911"),//部门选择 返回用户Name

    DEPARTMENT_REQUEST_ID(ChooseWay.MORE_CHOOSE,"912"),//部门选择 返回用户ID

    ORGANIZATION(ChooseWay.SINGLE_CHOOSE,"1001"); //组织机构选择 也就是路由选择  只限单选

    private Enum value;

    private String code;

    private TransitEnum(Enum value ,String code){
        this.value = value;
        this.code = code;
    }

    /**
     * 根据code 返回对应Value
     * @param code
     * @return
     */
    public Enum getValue(String code){

        for(TransitEnum mTransitEnum : TransitEnum.values())
        {
            if(mTransitEnum.code.equals(code))
            {
                return mTransitEnum.value;
            }
        }
        return null;
    }

    /**
     * 根据code 返回当前的Enum
     * @param code
     * @return
     */
    public Enum getTransitEnum(String code){
        for(TransitEnum mTransitEnum : TransitEnum.values())
        {
            if(mTransitEnum.code.equals(code))
            {
                return mTransitEnum;
            }
        }
        return null;
    }
}

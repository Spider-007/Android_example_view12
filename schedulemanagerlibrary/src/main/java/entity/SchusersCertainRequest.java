package entity;

import java.io.Serializable;

/**
 * Created by yanxin on 2018-3-30.
 * 参与人确认请求接口
 */
public class SchusersCertainRequest implements Serializable {
    public String userId;
    public long appId;
    public String schId;
}

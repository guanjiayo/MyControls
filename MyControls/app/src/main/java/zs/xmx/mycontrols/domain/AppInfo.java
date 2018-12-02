package zs.xmx.mycontrols.domain;

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2016/11/15 15:39
 * @本类描述	  ActivityName 的bean类
 * @内容说明
 * @补充内容
 *
 * ---------------------------------     
 * @新增内容
 *
 */
public class AppInfo {
    private String activityName;

    public AppInfo(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityName() {
        return activityName;
    }

    public AppInfo setActivityName(String activityName) {
        this.activityName = activityName;
        return this;
    }

    @Override
    public String toString() {
        return activityName ;
    }
}

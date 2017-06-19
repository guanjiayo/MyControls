package zs.xmx.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

import zs.xmx.factory.MyRemoteViewsFactory;

/*
 * @创建者     mqm
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2017/6/19
 * @本类描述	  ${TODO}
 * @内容说明   ${TODO}
 * @补充内容
 *
 * ---------------------------------     
 * @更新时间   
 * @新增内容   
 *
 */

public class UpdateService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new MyRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

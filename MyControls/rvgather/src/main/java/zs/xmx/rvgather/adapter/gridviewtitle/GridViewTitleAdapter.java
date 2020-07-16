package zs.xmx.rvgather.adapter.gridviewtitle;

import android.content.Context;

import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

import zs.xmx.rvgather.bean.GridViewTitle;

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @本类描述
 * @内容说明
 *
 */
public class GridViewTitleAdapter extends MultiItemTypeAdapter<GridViewTitle> {

    public GridViewTitleAdapter(Context context, List<GridViewTitle> datas) {
        super(context, datas);

        addItemViewDelegate(new HeadItemDelegate());
        addItemViewDelegate(new ContentItemDelegate(this,context));

    }
}

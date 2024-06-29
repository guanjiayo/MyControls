package zs.xmx.rvgather.adapter.gridviewtitle;

import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import zs.xmx.rvgather.R;
import zs.xmx.rvgather.bean.GridViewTitle;

/*
 * @创建者     默小铭
 * @本类描述
 * @内容说明
 *
 */
public class HeadItemDelegate implements ItemViewDelegate<GridViewTitle> {

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_head;
    }

    @Override
    public boolean isForViewType(GridViewTitle item, int position) {
        return item.getType() == GridViewTitle.TITLE;
    }

    @Override
    public void convert(ViewHolder holder, GridViewTitle gridViewTitle, int position) {
        holder.setText(R.id.tv_head_item, gridViewTitle.getData().getGroup());
    }


}

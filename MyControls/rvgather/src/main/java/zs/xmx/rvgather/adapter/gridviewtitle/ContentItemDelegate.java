package zs.xmx.rvgather.adapter.gridviewtitle;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

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
public class ContentItemDelegate implements ItemViewDelegate<GridViewTitle> {

    private int mSelectPosition = -1;
    private Context mContext;
    private GridViewTitleAdapter mAdapter;

    public ContentItemDelegate(GridViewTitleAdapter adapter, Context context) {
        this.mContext = context;
        this.mAdapter = adapter;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_content;
    }

    @Override
    public boolean isForViewType(GridViewTitle item, int position) {
        return item.getType() == GridViewTitle.CONTENT;
    }

    @Override
    public void convert(final ViewHolder holder, GridViewTitle gridViewTitle, final int position) {
        TextView tv_content = holder.getView(R.id.tv_content_item);
        tv_content.setText(gridViewTitle.getData().getGroup());

        tv_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectPosition = position;
                mAdapter.notifyDataSetChanged();
            }
        });

        Log.i("HAHA", "position: " + position + "  mSelectPosition: " + mSelectPosition);

        if (mSelectPosition == position) {
            tv_content.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        } else {
            tv_content.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
        }

    }


}

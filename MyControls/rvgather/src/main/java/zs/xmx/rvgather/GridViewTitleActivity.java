package zs.xmx.rvgather;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import zs.xmx.rvgather.adapter.gridviewtitle.GridViewTitleAdapter;
import zs.xmx.rvgather.bean.GridViewItem;
import zs.xmx.rvgather.bean.GridViewTitle;

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @本类描述
 * @内容说明
 *
 */
public class GridViewTitleActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview_title);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvList);
        initData();
    }

    private void initData() {
        final List<GridViewTitle> data = new ArrayList<>();

        List<String> list1 = new ArrayList<>();
        list1.add("标题1");
        list1.add("标题2");

        List<String> list2 = new ArrayList<>();
        list2.add("张三");
        list2.add("李四");
        list2.add("王五");

        for (int i = 0; i < list1.size(); i++) {
            data.add(new GridViewTitle(GridViewTitle.TITLE, new GridViewItem(list1.get(i)), 3));
            for (int j = 0; j < list2.size(); j++) {
                data.add(new GridViewTitle(GridViewTitle.CONTENT, new GridViewItem(list2.get(j)), 1));
            }
        }


        GridViewTitleAdapter adapter = new GridViewTitleAdapter(this, data);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return data.get(position).getSpan();
            }
        });

        mRecyclerView.setLayoutManager(gridLayoutManager);

        mRecyclerView.setAdapter(adapter);
    }
}

package zs.xmx.rvgather;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;

import zs.xmx.rvgather.adapter.chat.ChatAdapterForRv;
import zs.xmx.rvgather.bean.ChatMessage;

public class ChatRvActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;

    private LoadMoreWrapper mLoadMoreWrapper;
    private List<ChatMessage> mDatas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);


        mRecyclerView = findViewById(R.id.id_recyclerview);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDatas.addAll(ChatMessage.MOCK_DATAS);
        ChatAdapterForRv adapter = new ChatAdapterForRv(this, mDatas);

        mLoadMoreWrapper = new LoadMoreWrapper(adapter);
        //添加Loading布局
        mLoadMoreWrapper.setLoadMoreView(LayoutInflater.from(this).inflate(R.layout.default_loading, mRecyclerView, false));
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        boolean coming = Math.random() > 0.5;
                        ChatMessage msg = null;
                        msg = new ChatMessage(coming ? R.drawable.renma : R.drawable.xiaohei, coming ? "人马" : "xiaohei", "where are you " + mDatas.size(),
                                null, coming);
                        mDatas.add(msg);
                        mLoadMoreWrapper.notifyDataSetChanged();

                    }
                }, 3000);
            }
        });

        adapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(ChatRvActivity.this, "Click:" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(ChatRvActivity.this, "LongClick:" + position, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        mRecyclerView.setAdapter(mLoadMoreWrapper);
    }


}

package zs.xmx.wygridlayout;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String>     mTitles;
    private DragedGridLayout mDragedGridLayout;
    private DragedGridLayout mHideGridLayout;
    private List<String>     mNewtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();

    }

    private void initView() {
        setContentView(R.layout.activity_main);
        mDragedGridLayout = (DragedGridLayout) findViewById(R.id.dragedgridlayout);

        mDragedGridLayout.setAllowDrag(true);
        mDragedGridLayout.setItem(mTitles);
        mDragedGridLayout.setOnDragItemClickListener(new DragedGridLayout.onDragItemClickListener() {
            @Override
            public void onDragItemClick(TextView textView) {
                //移除view,由于removeView需要时间,因此不能直接addView,否者会报错
                mDragedGridLayout.removeView(textView);
                mHideGridLayout.addItem(textView.getText().toString());
            }
        });

        mHideGridLayout = (DragedGridLayout) findViewById(R.id.hidegridlayout);

        mHideGridLayout.setAllowDrag(false);
        mHideGridLayout.setItem(mNewtitle);
        mHideGridLayout.setOnDragItemClickListener(new DragedGridLayout.onDragItemClickListener() {
            @Override
            public void onDragItemClick(TextView textView) {
                //添加view
                mHideGridLayout.removeView(textView);
                mDragedGridLayout.addItem(textView.getText().toString());
            }
        });
    }

    private void initData() {
        //定义出频道数据
        mTitles = new ArrayList<>();
        mTitles.add("上海");
        mTitles.add("北京");
        mTitles.add("广州");
        mTitles.add("深圳");
        mTitles.add("泉州");
        mTitles.add("漳州");
        mTitles.add("昆明");
        mTitles.add("西安");
        mTitles.add("昆仑");
        mTitles.add("峨眉");
        mTitles.add("神农架");

        //新的频道数据

        mNewtitle = new ArrayList<>();
        mNewtitle.add("中山");
        mNewtitle.add("湛江");
        mNewtitle.add("长江");
        mNewtitle.add("东莞");
        mNewtitle.add("茂名");
        mNewtitle.add("汕头");
        mNewtitle.add("长白山");
        mNewtitle.add("大兴安岭");

    }


}

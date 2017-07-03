package zs.xmx;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/*
 * @创建者     xmx
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2017/7/3
 * @本类描述	  ScrollView 嵌套布局(软键盘测试)
 * @内容说明   1. 用ScrollView嵌套的布局,当弹出软键盘时,ScrollView布局上移,但不会超出屏幕
 *            2. xml布局文件不能直接把宽高之类的参数定死
 *            3. 使用了ScrollView嵌套,不需要在清单文件设置android:windowSoftInputMode
 * @补充内容
 *
 * ---------------------------------
 * @更新时间
 * @新增内容
 *
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private String[] itemName = {"ScrollView 嵌套布局(软键盘测试)", "adjustResize(软键盘测试)",
            "adjustPan(软键盘测试)", "跟随软键盘移动整个界面", "全屏模式,输入框上移但标题栏不会挤到屏幕外"};
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new ClassAdapter(this, R.layout.item_list, itemName));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, Scroll_keyboardActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, AdjustResize_KeyboardActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, AdjustPan_KeyboardActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, Allup_KeyboardActivity.class));
                break;
        }
    }

    class ClassAdapter extends ArrayAdapter<String> {

        private int mResourceId;

        public ClassAdapter(Context context, int textViewResourceId,
                            String[] className) {
            super(context, textViewResourceId, className);
            this.mResourceId = textViewResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String item = getItem(position);
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(mResourceId, null);
            TextView text = (TextView) view.findViewById(R.id.tv_item);
            text.setText(item);
            return view;
        }
    }
}

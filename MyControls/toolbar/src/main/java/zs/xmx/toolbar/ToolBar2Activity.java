package zs.xmx.toolbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import zs.xmx.toolbar.utils.StatusBar;

/**
 * 将ToolBar当作actionBar使用
 */
public class ToolBar2Activity extends AppCompatActivity {
    private TextView mTextView;
    private Toolbar  toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

        initEvent();

    }

    private void initEvent() {
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();
                if (menuItemId == R.id.action_search) {
                    Toast.makeText(ToolBar2Activity.this, R.string.search_menu, Toast.LENGTH_SHORT).show();

                } else if (menuItemId == R.id.action_notification) {
                    Toast.makeText(ToolBar2Activity.this, R.string.notification_menu, Toast.LENGTH_SHORT).show();

                } else if (menuItemId == R.id.action_item1) {
                    Toast.makeText(ToolBar2Activity.this, R.string.item1_menu, Toast.LENGTH_SHORT).show();

                } else if (menuItemId == R.id.action_item2) {
                    Toast.makeText(ToolBar2Activity.this, R.string.item2_menu, Toast.LENGTH_SHORT).show();

                }
                return true;
            }
        });

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ToolBar2Activity.this, R.string.controls_toolbar, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 初始化界面
     */
    private void initView() {
        setContentView(R.layout.activity_toolbar2);
        StatusBar.setTransparentStatusBar(this); //设置透明的状态栏
        mTextView = (TextView) findViewById(R.id.tv_user2);
        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        toolbar.setNavigationIcon(R.mipmap.ic_drawer_home);//设置导航栏图标
        toolbar.setLogo(R.mipmap.ic_launcher);//设置app logo
        toolbar.setTitle("Title");//设置主标题
        toolbar.setSubtitle("Subtitle");//设置子标题

        setSupportActionBar(toolbar);
    }

    /**
     * ToolBar 替换ActionBar 方式要用这种方式实现Menu布局,不然没效果
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}

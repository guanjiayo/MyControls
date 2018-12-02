package zs.xmx.toolbar;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import zs.xmx.toolbar.utils.StatusBar;

/**
 * ToolBar作为独立控件使用
 */
public class ToolBarActivity extends AppCompatActivity {
    private Button  btn;
    private Toolbar toolbar;

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
                    Toast.makeText(ToolBarActivity.this, R.string.search_menu, Toast.LENGTH_SHORT).show();

                } else if (menuItemId == R.id.action_notification) {
                    Toast.makeText(ToolBarActivity.this, R.string.notification_menu, Toast.LENGTH_SHORT).show();

                } else if (menuItemId == R.id.action_item1) {
                    Toast.makeText(ToolBarActivity.this, R.string.item1_menu, Toast.LENGTH_SHORT).show();

                } else if (menuItemId == R.id.action_item2) {
                    Toast.makeText(ToolBarActivity.this, R.string.item2_menu, Toast.LENGTH_SHORT).show();

                }
                return true;
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ToolBarActivity.this, R.string.controls_toolbar, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 初始化界面
     */
    private void initView() {
        setContentView(R.layout.activity_toolbar);
        StatusBar.setTransparentStatusBar(this); //设置透明的状态栏
        btn = (Button) findViewById(R.id.btn_user);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.toolbar_menu);//设置右上角的填充菜单(替换ActionBar方式不调用)

    }


}

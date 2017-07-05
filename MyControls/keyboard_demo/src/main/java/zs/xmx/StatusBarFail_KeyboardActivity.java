package zs.xmx;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import zs.xmx.utils.StatusBar;

/*
 * @创建者     xmx
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2017/7/3
 * @本类描述   AdjustResize失效处理(沉浸式状态栏)
 * @内容说明   解决: 1. 使用5497类(FrameLayout_KeyboardUtils),动态监听软键盘弹出位置
 *                  2. 在设置了沉浸式状态栏的Actvity对应的XML布局文件加上android:fitsSystemWindows="true"属性(建议)
 *
 *
 *
 * @补充内容
 *
 * ---------------------------------
 * @更新时间
 * @新增内容
 *
 */
public class StatusBarFail_KeyboardActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statusbarfail);
        //FrameLayout_KeyboardUtils.assistActivity(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        StatusBar.setStatusBarColor(this,Color.RED);
    }
}
